# STAGE 1: BUILDER - Image complète pour compiler (même si on copie le JAR, c'est une bonne pratique)
FROM eclipse-temurin:17-jdk-focal AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# STAGE 2: RUNNER - Utilisation de l'image minimale pour l'exécution
FROM eclipse-temurin:17-jre-focal

# Définition du répertoire de travail
WORKDIR /app

# Copie du fichier JAR compilé depuis l'étage de build
COPY --from=builder /app/target/service-profils-graphql-1.0.0.jar app.jar

# Le service Profils utilise le port 4000
EXPOSE 4000

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]