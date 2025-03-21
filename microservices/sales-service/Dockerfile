FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY pom.xml .
RUN apk add maven
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/sales-service-1.0-SNAPSHOT.jar"] 