# STAGE 1: BUILDER - Image complète pour compiler (même si on copie le JAR, c'est une bonne pratique)
FROM eclipse-temurin:17-jdk-focal AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# STAGE 2: RUNNER - Utilisation de l'image minimale pour l'exécution
# Utilisation de l'image JRE seulement pour réduire la taille du conteneur final
FROM eclipse-temurin:17-jre-focal

# Définition du répertoire de travail dans le conteneur
WORKDIR /app

# Copie du fichier JAR compilé depuis l'étage de build
COPY --from=builder /app/target/service-mobilite-rest-1.0.0.jar app.jar

# Le service Mobilité utilise le port 8081
EXPOSE 8081

# Commande pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]