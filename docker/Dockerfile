FROM openjdk:11-jre-slim
MAINTAINER Marcin Sarniak <marcin.sarniak.pl@gmail.com>

ENV JAVA_OPTS="-server -Dspring.profiles.active=docker"
ENV APP_DIR="/opt/shop"
ENV APP_JAR="msarniak-shop.jar"

RUN mkdir -p $APP_DIR

WORKDIR $APP_DIR

ADD msarniak-shop.jar $APP_DIR/msarniak-shop.jar

EXPOSE 8080 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar $APP_JAR" ]
