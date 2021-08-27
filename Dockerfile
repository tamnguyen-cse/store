FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG EUREKA_IP_ADDRESS
ENV EUREKA_HOST=$EUREKA_IP_ADDRESS
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
