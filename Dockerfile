# 使用支持多架构的 JRE 作为基础镜像
FROM eclipse-temurin:22-jre
ENV TZ=Asia/Shanghai
WORKDIR /app

# 安装时区支持和字体 (这些是运行时必须的)
RUN apt-get update && apt-get install -y --no-install-recommends \
    tzdata fontconfig fonts-dejavu-core curl && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone && \
    rm -rf /var/lib/apt/lists/*

# 直接拷贝 GitHub Action 阶段已经编译好的通用字节码产物
# 这里的路径对应 unzip 后的结构
COPY read.jar ./read.jar
COPY libs/ ./libs/

EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "/app/read.jar"]