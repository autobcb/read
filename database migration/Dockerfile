# 使用一个轻量的官方 Python 镜像作为基础
FROM python:3.9-slim

# 安装 mysql-connector-python 库
RUN pip install mysql-connector-python

# 设置容器内的工作目录
WORKDIR /app

# 将当前目录下的所有文件（脚本、配置等）复制到容器的 /app 目录中
COPY . .

# 设置容器启动时要执行的默认命令
CMD ["python", "migration.py"]
