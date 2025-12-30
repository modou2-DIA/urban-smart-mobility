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

# Define environment variables for inter-container communication
ENV GRPC_HOST=urban-emergency-grpc-service
ENV GRPC_PORT=9090
ENV SOAP_HOST=urban-soap-service
ENV SOAP_PORT=8081
ENV REST_HOST=urban-rest-service
ENV REST_PORT=8082
ENV GRAPHQL_HOST=urban-graphql-service
ENV GRAPHQL_PORT=8083

EXPOSE 8080 
ENTRYPOINT ["java", "-jar", "/app.jar"]