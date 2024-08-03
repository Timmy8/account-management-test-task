FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package

FROM eclipse-temurin:17.0.12_7-jdk-jammy

RUN adduser --system run-user && addgroup --system management-application && adduser run-user management-application
USER run-user

WORKDIR /app

COPY docker-compose.yml docker-compose.yml
COPY --from=build build/target/account-management.jar ./application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./application.jar"]