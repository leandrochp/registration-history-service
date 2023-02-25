FROM gradle:5.3.1-jdk8-alpine AS BUILD_IMAGE
COPY . /home/source/java
WORKDIR /home/source/java
USER root
RUN chown -R gradle /home/source/java
USER gradle
RUN gradle clean build

FROM openjdk:8-jre-alpine

ENV APPLICATION_USER vertx
RUN adduser $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY --from=BUILD_IMAGE /home/source/java/build/libs/registration-history-service-1.0.jar /app/registration-history-service.jar
WORKDIR /app

CMD ["java", "-server", "-jar", "registration-history-service-1.0.0.jar"]
