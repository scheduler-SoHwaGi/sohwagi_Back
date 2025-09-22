#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app"
JAR_FILE="$PROJECT_ROOT/sohwagi-app.jar"
GC_LOG_PATH="$PROJECT_ROOT/gc.log"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 실행 시작" >> $DEPLOY_LOG
nohup java -Xlog:gc*:file=$GC_LOG_PATH:time,uptimemillis:filecount=10,filesize=10m -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 PID: $CURRENT_PID" >> $DEPLOY_LOG
