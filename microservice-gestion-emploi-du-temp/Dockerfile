#FROM openjdk:11
#COPY target/microservice-utilisateur-0.0.1-SNAPSHOT.jar /app.jar
#EXPOSE 9001
#ENTRYPOINT ["java","-jar","app.jar"]



From openjdk:11
VOLUME /tmp
COPY target/microservice-utilisateur-0.0.1-SNAPSHOT.jar /jilms.jar
CMD ["java","-jar","/jilms.jar","--spring.profiles.active=dev"]
EXPOSE 9001