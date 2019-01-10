FROM openjdk:8-jre-alpine

MAINTAINER Andriy Kornatskyy <andriy.kornatskyy@live.com>

ENV APP_VERSION=1.0-SNAPSHOT

RUN set -ex \
    \
    && apk add --no-cache --virtual .build-deps \
        git \
        openjdk8 \
        maven \
    \
    && git clone https://github.com/akornatskyy/sample-blog-api-java.git src \
    \
    && cd src \
    && mvn -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -T"$(getconf _NPROCESSORS_ONLN)" verify \
    && mkdir -p /app \
    && mv /src/sample-blog-app/target/sample-blog-app-${APP_VERSION}.jar /app \
    \
    && rm -rf /src ~/.m2 \
    && apk del .build-deps

CMD java -Xms64M -Xmx64M -jar /app/sample-blog-app-$APP_VERSION.jar
