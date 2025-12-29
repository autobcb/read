# 数据库迁移操作说明 (SQLite -> MySQL)

本文档指导您如何使用提供的 Python 脚本和 Docker Compose，将 `read` 应用的数据从 SQLite 数据库迁移到 MySQL 数据库。

---

### **第一步：准备工作**

1.  **备份 SQLite 数据库**：这是最重要的步骤。请将您的 `read.db` 文件（位于 `read` 项目根目录）复制一份到安全的地方。

2.  **安装 Docker 和 Docker Compose**：确保您的系统上已正确安装并正在运行 Docker 和 Docker Compose。

---

### **第二步：修改配置文件**

您需要修改 `read/database migration` 目录下的 `config.ini` 和 `docker-compose.yml` 文件。

1.  **编辑 `docker-compose.yml`**：
    *   在 `mysql` 服务下，找到 `environment` 部分。
    *   将 `your_mysql_root_password` 替换为您的 MySQL `root` 密码。
    *   将 `your_new_database_name` 替换为您想创建的数据库名（例如 `read_db`）。

2.  **编辑 `config.ini`**：
    *   **重要**：将 `mysql_host` 的值修改为 `mysql` (这是 Docker Compose 中 MySQL 服务的名称)。
    *   将其余的 `mysql_user`, `mysql_password`, 和 `mysql_database` 修改为与 `docker-compose.yml` 中一致的值。
        *   如果您想使用 `root` 用户进行迁移，请确保 `mysql_user` 为 `root`，`mysql_password` 为您设置的 `root` 密码。
    *   `sqlite_path` 应指向容器内部 `read.db` 文件的路径。脚本和 `docker-compose.yml` 已将其配置为 `/app/read.db`，您**无需修改**此项。

    修改后的 `config.ini` 示例：
    ```ini
    [DATABASE]
    # SQLite 数据库文件路径 (在 Docker 容器内部的路径)
    sqlite_path = /app/read.db
    
    # MySQL 连接信息
    mysql_host = mysql
    mysql_port = 3306
    mysql_user = root
    mysql_password = your_mysql_root_password
    mysql_database = your_new_database_name
    ```

---

### **第三步：自动创建 MySQL 表结构**

为了确保 MySQL 数据库拥有正确的表结构，您需要让 `read` 服务自动创建。

1.  **修改 `read` 服务配置**：
    *   导航到 `read/src/main/resources/` 目录。
    *   编辑 `app.yml`，将 `solon.config.add: "./conf.yml"` 改为 `solon.config.add: "./mysqlconf.yml"`。

2.  **修改 MySQL 连接配置**：
    *   导航到 `read/conf/` 目录。
    *   编辑 `mysqlconf.yml`，填入您的 MySQL 数据库的连接信息。
        ```yaml
        solon.dataSources:
          db:
            class: "com.zaxxer.hikari.HikariDataSource"
            driverClassName: com.mysql.cj.jdbc.Driver
            # 主机使用 127.0.0.1 或 localhost，因为它将通过 Docker 的端口映射连接
            jdbcUrl: jdbc:mysql://127.0.0.1:3306/your_new_database_name?characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=UTC 
            username: root # 使用 root 用户
            password: your_mysql_root_password # 使用您设置的 root 密码
        ```

3.  **启动 `read` 服务以建表**：
    *   **在启动 `read` 服务之前，请先单独启动 MySQL 容器**。在 `read/database migration` 目录下运行：
        ```bash
        docker-compose up -d mysql
        ```
        等待几十秒，确保 MySQL 服务完全启动。
    *   现在，从 `read` 服务的根目录正常启动 `read` 服务（例如，通过 `java -jar build/libs/read-1.0-SNAPSHOT.jar`）。
    *   服务成功连接到 MySQL 后，`auto-table-core` 会自动创建所有表。
    *   确认表已创建后（可以通过日志或连接数据库查看），**立即停止** `read` 服务。

---

### **第四步：运行 Docker 迁移**

1.  **打开命令行工具**：
    *   导航到 `read/database migration` 目录。
    *   执行以下命令来启动迁移脚本（由于 `mysql` 服务已在运行，此命令只会构建并运行迁移脚本）：
        ```bash
        docker-compose up --build migration
        ```
    *   您将看到终端打印日志，显示数据从 SQLite 导出并导入到 MySQL 的过程。

2.  **等待脚本完成**：
    *   迁移脚本容器 (`db_migration_script`) 会在完成数据导入后自动停止并退出。

---

### **第五步：验证和收尾**

1.  **验证数据**：
    *   您可以使用 MySQL 客户端连接到数据库，检查数据是否已成功导入。

2.  **清理 Docker 容器**：
    *   如果您确定数据已迁移成功，可以停止并移除 Docker Compose 创建的服务：
        ```bash
        docker-compose down
        ```

3.  **最终启动 `read` 服务**：
    *   现在，您的 `read` 服务已经配置为连接到 MySQL 数据库，并且数据也已导入。您可以正常启动 `read` 服务。
