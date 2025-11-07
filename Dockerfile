FROM eclipse-temurin:22-jdk-jammy

ENV TZ=Asia/Shanghai

ARG DOWNLOAD_URL=https://github.com/autobcb/read/releases/download/3.2.2/web-3.2.2b2.zip
ENV DOWNLOAD_URL=${DOWNLOAD_URL}

USER root

# 安装必要工具（wget、unzip、字体、证书）和 envsubst 支持
RUN apt update && \
    apt install -y --no-install-recommends \
        wget unzip \
        fonts-wqy-microhei fonts-wqy-zenhei xfonts-wqy \
        ca-certificates gettext-base && \
    rm -rf /var/lib/apt/lists/* && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone

# 使用 /app 作为工作目录
WORKDIR /app

# 拷贝 entrypoint 和 conf 模板
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# 暴露端口
EXPOSE 8080

# 使用 entrypoint 脚本启动
ENTRYPOINT ["/entrypoint.sh"]



