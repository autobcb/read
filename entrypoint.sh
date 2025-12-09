#!/bin/sh
set -euo pipefail

CONFIG_FILE=/app/conf.yml
APP_DIR=/app
DATA_DIR=/data
SOURCE_DIR=/app_default   # 镜像中保留的原始文件副本（在构建时复制进去）

mkdir -p "$DATA_DIR"

echo "[INFO] === 启动前清理与同步最新文件 ==="

# ==========================================================
# 自动清理宿主机旧文件并更新
# ==========================================================
if [[ -d "$APP_DIR/libs" || -f "$APP_DIR/read.jar" ]]; then
    echo "[INFO] 删除宿主机旧的 libs/ 与 read.jar"
    rm -rf "$APP_DIR/libs" "$APP_DIR/read.jar"
fi

# 从镜像内置文件复制最新版本
if [[ -d "$SOURCE_DIR" ]]; then
    echo "[INFO] 同步最新程序文件..."
    cp -r "$SOURCE_DIR/libs" "$APP_DIR/" 2>/dev/null || true
    cp "$SOURCE_DIR/read.jar" "$APP_DIR/" 2>/dev/null || true
else
    echo "[WARN] 未找到镜像内默认文件目录 $SOURCE_DIR，跳过更新"
fi

# ==========================================================
# 下载 read.jar（若不存在）
# ==========================================================
if [[ ! -f "$APP_DIR/read.jar" ]]; then
    echo "[INFO] read.jar 不存在，尝试从 DOWNLOAD_URL 下载..."
    if [[ -z "${DOWNLOAD_URL:-}" ]]; then
        echo "[ERROR] DOWNLOAD_URL 未设置，无法下载 read.jar"
        exit 1
    fi
    wget --no-check-certificate -O /tmp/web.zip "$DOWNLOAD_URL"
    unzip -q /tmp/web.zip -d /tmp/web_temp
    cp -r /tmp/web_temp/* "$APP_DIR/"
    rm -rf /tmp/web.zip /tmp/web_temp
fi

# ------------------------------
# 删除镜像内置的默认配置
# ------------------------------
if [[ -f $CONFIG_FILE ]]; then
  echo "[INFO] 删除镜像内置的 $CONFIG_FILE"
  rm -f "$CONFIG_FILE"
fi

# ------------------------------
# 根据 DB_TYPE 生成配置（若不存在）
# ------------------------------
DB_TYPE="${DB_TYPE:-sqlite}"

if [[ ! -f "$CONFIG_FILE" ]]; then
    echo "[INFO] 未检测到宿主机配置文件，生成默认 $CONFIG_FILE"
    {
        echo "solon.dataSources:"
        echo "  db:"
        echo "    class: \"com.zaxxer.hikari.HikariDataSource\""
        if [[ "$DB_TYPE" == "sqlite" ]]; then
            echo "    driverClassName: org.sqlite.JDBC"
            echo "    jdbcUrl: jdbc:sqlite:/data/read.db"
            echo "    maxPoolSize: 1"
        else
            echo "    driverClassName: com.mysql.cj.jdbc.Driver"
            echo "    jdbcUrl: ${DB_JDBCURL:-jdbc:mysql://127.0.0.1:3306/read?characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=UTC}"
            echo "    username: ${DB_USERNAME:-root}"
            echo "    password: ${DB_PASSWORD:-password}"
            echo "    maxPoolSize: ${DB_MAX_POOL:-10}"
        fi

        cat <<EOF
admin:
  username: "${ADMIN_USERNAME:-admin}"
  password: "${ADMIN_PASSWORD:-adminadmin}"
  code: "${ADMIN_CODE:-}"

user:
  allowuptxt: ${USER_ALLOWUPTXT:-false}
  allowcache: ${USER_ALLOWCACHE:-false}
  allowimg: ${USER_ALLOWIMG:-false}
  allowcheck: ${USER_ALLOWCHECK:-false}
  source: ${USER_SOURCE:-0}
  maxsource: ${USER_SOURCE:-0}
  timeout: ${USER_TIMEOUT:-0}
  proxypng: ${USER_PROXYPNG:-false}

smtp:
  host: "${SMTP_HOST:-}"
  protocols: "${SMTP_PROTOCOLS:-TLSv1.2}"
  port: "${SMTP_PORT:-}"
  account: "${SMTP_ACCOUNT:-}"
  password: "${SMTP_PASSWORD:-}"
  personal: "${SMTP_PERSONAL:-}"
  codesubject: "${SMTP_CODESUBJECT:-验证码}"
  codetext: "${SMTP_CODETEXT:-您的验证码是\$code,当前验证码十分钟有效请尽快使用}"

default:
  tts: "${DEFAULT_TTS:-}"
  rule: "${DEFAULT_RULE:-}"

server.http:
  coreThreads: "x${SERVER_HTTP_CORETHREADS:-5}"
  maxThreads: "x${SERVER_HTTP_MAXTHREADS:-10}"

EOF
    } > "$CONFIG_FILE"
else
    echo "[INFO] 检测到宿主机挂载的配置文件，使用已有 $CONFIG_FILE"
fi

# ------------------------------
# Debug 模式：打印配置
# ------------------------------
if [[ "${DEBUG:-false}" == "true" ]]; then
    echo "========== [DEBUG] config.yml =========="
    cat "$CONFIG_FILE"
    echo "========================================"
fi

# ------------------------------
# 启动应用
# ------------------------------
echo "[INFO] 启动 read.jar..."
exec java -jar "$APP_DIR/read.jar"
