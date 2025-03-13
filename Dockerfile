FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


# From openjdk:11
# VOLUME /tmp
# ADD target/microservice-utilisateur-0.0.1-SNAPSHOT.jar /jilms.jar
# CMD ["java","-jar","/jilms.jar","--spring.profiles.active=dev"]
# EXPOSE 9001