import sqlite3
import mysql.connector
import configparser
import json
import logging

# --- 配置日志 ---
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

# --- 表名和导入顺序 ---
# 根据源码分析，确保依赖关系正确 (例如，users 表在 booklist 之前)
TABLE_ORDER = [
    'users',
    'book_group',
    'book_source',
    'rss_source',
    'http_tts',
    'replace_rule',
    'booklist',
    'usertocken',
    'bookmark',
    'book_cache',
    'item',
    'back_ground',
    'code',
    'user_book_source',
    'user_rss_source'
]

def read_config(filename='config.ini'):
    """读取数据库配置"""
    config = configparser.ConfigParser()
    if not config.read(filename):
        raise FileNotFoundError(f"配置文件 {filename} 未找到或无法读取。")
    return config['DATABASE']

def export_from_sqlite(db_path):
    """从 SQLite 导出所有表的数据"""
    try:
        conn = sqlite3.connect(db_path)
        conn.row_factory = sqlite3.Row
        cursor = conn.cursor()
        
        all_data = {}
        logging.info("开始从 SQLite 导出数据...")
        
        for table_name in TABLE_ORDER:
            try:
                cursor.execute(f"SELECT * FROM {table_name}")
                rows = cursor.fetchall()
                all_data[table_name] = [dict(row) for row in rows]
                logging.info(f"  - 成功导出表 '{table_name}' ({len(rows)} 行)")
            except sqlite3.OperationalError as e:
                logging.warning(f"  - 导出表 '{table_name}' 时出错: {e}。可能是该表不存在，已跳过。")
                all_data[table_name] = []

        conn.close()
        logging.info("SQLite 数据导出完成。")
        return all_data
    except Exception as e:
        logging.error(f"连接到 SQLite 数据库 '{db_path}' 失败: {e}")
        return None

def import_to_mysql(data, config):
    """将数据导入到 MySQL"""
    try:
        conn = mysql.connector.connect(
            host=config['mysql_host'],
            port=config.getint('mysql_port', 3306),
            user=config['mysql_user'],
            password=config['mysql_password'],
            database=config['mysql_database']
        )
        cursor = conn.cursor()
        logging.info("已连接到 MySQL 数据库。准备导入数据...")

        # 禁用外键检查，以避免因导入顺序产生的问题
        cursor.execute("SET foreign_key_checks = 0;")
        logging.info("已禁用外键检查。")

        for table_name in TABLE_ORDER:
            table_data = data.get(table_name)
            if not table_data:
                logging.info(f"  - 表 '{table_name}' 无数据，已跳过。")
                continue

            # 获取列名和占位符
            columns = table_data[0].keys()
            placeholders = ', '.join(['%s'] * len(columns))
            sql = f"INSERT INTO `{table_name}` ({', '.join(f'`{col}`' for col in columns)}) VALUES ({placeholders})"

            # 准备要插入的数据列表
            values_to_insert = []
            for row in table_data:
                # 处理布尔值: Python的 True/False 在 mysql-connector 中会自动转为 1/0
                # 处理 JSON/复杂对象: 假设所有非基本类型都以文本形式存储
                row_values = []
                for col in columns:
                    val = row.get(col)
                    if isinstance(val, (dict, list)):
                        row_values.append(json.dumps(val))
                    else:
                        row_values.append(val)
                values_to_insert.append(tuple(row_values))

            try:
                # 批量插入数据
                cursor.executemany(sql, values_to_insert)
                conn.commit()
                logging.info(f"  - 成功导入表 '{table_name}' ({len(values_to_insert)} 行)")
            except mysql.connector.Error as err:
                logging.error(f"  - 导入表 '{table_name}' 时发生错误: {err}")
                conn.rollback() # 回滚此表的事务

        # 重新启用外键检查
        cursor.execute("SET foreign_key_checks = 1;")
        logging.info("已重新启用外键检查。")
        
        conn.close()
        logging.info("数据导入成功完成！")

    except mysql.connector.Error as err:
        logging.error(f"连接或操作 MySQL 数据库时出错: {err}")

def main():
    """主函数"""
    try:
        db_config = read_config()
        
        # 步骤 1: 导出
        sqlite_data = export_from_sqlite(db_config['sqlite_path'])
        
        if sqlite_data:
            # 步骤 2: 导入
            import_to_mysql(sqlite_data, db_config)

    except Exception as e:
        logging.error(f"迁移过程中发生意外错误: {e}")

if __name__ == '__main__':
    main()
