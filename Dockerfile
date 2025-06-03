FROM ubuntu:latest
VOLUME /tmp
LABEL authors="alvar"

ARG JAR_FILE=target/mi-app.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
