# lucasg04/library-management-backend
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY target/library_management-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
