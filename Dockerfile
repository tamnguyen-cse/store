FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar app.jar
COPY entrypoint.sh /usr/local/bin/
ENTRYPOINT ["java","-jar","/app.jar"]
