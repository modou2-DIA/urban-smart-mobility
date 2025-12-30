# Dockerfile pour l'API Gateway
FROM maven:3.9.6-eclipse-temurin-17-focal AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-focal
COPY --from=build /app/target/*.jar app.jar

# La Gateway doit toujours écouter sur 8080 (c'est le port public)
EXPOSE 8080 
ENTRYPOINT ["java", "-jar", "/app.jar"]