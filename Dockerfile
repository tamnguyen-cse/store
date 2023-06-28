FROM openjdk:17-oracle
VOLUME /tmp
ARG PORT
ENV PORT=$PORT
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
