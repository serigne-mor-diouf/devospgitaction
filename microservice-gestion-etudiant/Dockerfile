#FROM openjdk:11
#COPY target/microservice-utilisateur-0.0.1-SNAPSHOT.jar /app.jar
#EXPOSE 9001
#ENTRYPOINT ["java","-jar","app.jar"]

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar
ENV SERVER_PORT=9003
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]