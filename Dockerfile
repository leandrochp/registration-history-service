FROM gradle:7.1.0-jdk11 AS BUILD_IMAGE
COPY . /home/source/java
WORKDIR /home/source/java
USER root
RUN chown -R gradle /home/source/java
USER gradle
RUN gradle clean build

FROM openjdk:11

ENV APPLICATION_USER vertx
RUN adduser $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY --from=BUILD_IMAGE /home/source/java/build/libs/registration-history-service-1.0.jar /app/registration-history-service.jar
WORKDIR /app

ENV SERVER_PORT=7000
ENV DATABASE_HOST=localhost
ENV DATABASE_NAME=registration_history_service
ENV DATABASE_AUTH_USER=registry
ENV DATABASE_AUTH_PASSWORD=passw0rd

CMD ["java", "-server", "-jar", "registration-history-service-1.0.0.jar"]
