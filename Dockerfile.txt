FROM eclipse-temurin:22-jre-alpine
ENV TZ=Asia/Shanghai
ARG DOWNLOAD_URL=https://github.com/autobcb/read/releases/download/3.3.4/web-3.3.4.zip
ENV DOWNLOAD_URL=${DOWNLOAD_URL}

USER root

# 安装基础工具（wget、unzip、证书、时区、中文字体）
RUN apk add --no-cache \
      wget unzip ca-certificates tzdata fontconfig ttf-dejavu && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone && \
    # 清理缓存
    rm -rf /var/cache/apk/*

# 使用 /app 作为工作目录
WORKDIR /app

# 拷贝 entrypoint 和 conf 模板
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# 暴露端口
EXPOSE 8080

# 使用 entrypoint 脚本启动
ENTRYPOINT ["/entrypoint.sh"]



