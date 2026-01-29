# Dockerfile pour le backend Spring Boot
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /workspace

# Copier le pom.xml du backend
COPY pom.xml /workspace/back/pom.xml

# Copier le code source du backend
COPY src /workspace/back/src

# Construire uniquement le module backend (sans les autres modules)
WORKDIR /workspace/back
RUN mvn clean package -Dmaven.test.skip=true

# Image de production
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Créer un utilisateur non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copier le JAR depuis l'étape de build
COPY --from=build /workspace/back/target/wacdo-0.0.1-SNAPSHOT.jar app.jar

# Créer le répertoire pour les uploads et logs (avant de changer d'utilisateur)
USER root
RUN mkdir -p /app/logs && \
    chown -R spring:spring /app/logs
USER spring:spring

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT ["java", "-Dspring-boot.run.profiles=${SPRING_PROFILES_ACTIVE}  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar","app.jar"]

#ENTRYPOINT ["java", "-jar", "app.jar"]