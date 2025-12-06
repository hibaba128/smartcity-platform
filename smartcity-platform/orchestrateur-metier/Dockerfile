# STAGE 1: BUILDER - Image complète pour compiler le projet
FROM eclipse-temurin:17-jdk-focal AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# STAGE 2: RUNNER - Image minimale pour l'exécution de l'application
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copie du fichier JAR compilé depuis l'étage de build
COPY --from=builder /app/target/orchestrateur-metier-1.0.0.jar app.jar

EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]