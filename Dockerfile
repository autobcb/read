# Stage 1: Build
FROM eclipse-temurin:22-jdk AS build
WORKDIR /build

# 禁用工具链自动下载，强制使用镜像自带的 JDK
ENV GRADLE_OPTS="-Dorg.gradle.java.installations.auto-download=false"

# 1. 缓存 Gradle 依赖
COPY gradle/ gradle/
COPY gradlew build.gradle.kts settings.gradle.kts gradle.properties ./
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

# 2. 拷贝本地源码并构建
# .dockerignore 会排除本地的 build 和 .gradle 文件夹
COPY . .
RUN ./gradlew build -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:22-jre
ENV TZ=Asia/Shanghai
WORKDIR /app

# 安装时区支持和字体
RUN apt-get update && apt-get install -y --no-install-recommends \
    tzdata fontconfig fonts-dejavu-core && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone && \
    rm -rf /var/lib/apt/lists/*

# 3. 拷贝构建产物
# 主 JAR 位于 build/libs/，依赖库位于 libs/
COPY --from=build /build/build/libs/solon-read-1.0-SNAPSHOT.jar ./read.jar
COPY --from=build /build/libs/ ./libs/

EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "read.jar"]
