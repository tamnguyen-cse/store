FROM openjdk:8-jdk-alpine
VOLUME /tmp
ENV EUREKA_IP_ADDRESS=${EUREKA_IP_ADDRESS}
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
