# PHASE 1: BUILD
FROM maven:3.9.6-eclipse-temurin-17-focal AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn package -DskipTests

# PHASE 2: RUN
FROM eclipse-temurin:17-jre-focal
COPY --from=build /app/target/*.jar app.jar

# Configuration du port d'écoute Spring Boot (8082)
ENV SERVER_PORT=8082
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app.jar"]