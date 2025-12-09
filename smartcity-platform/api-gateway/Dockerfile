# PHASE DE CONSTRUCTION (BUILDER)
# Utilise une image Maven/JDK 17 pour compiler le code
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
# Copie les fichiers de configuration et le code source
COPY pom.xml .
COPY src ./src
# Construit l'application et crée le fichier JAR
RUN mvn clean package -DskipTests

# ---

# PHASE D'EXÉCUTION (RUNNER)
# Utilise une image JRE 17 légère pour l'exécution (plus petite)
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
# Copie le fichier JAR compilé depuis l'étape 'builder'
COPY --from=builder /app/target/*.jar app.jar

# Le port par défaut de Spring Boot est 8080
# La Gateway écoutera sur ce port à l'intérieur du conteneur.
EXPOSE 8080

# Commande pour exécuter le JAR
ENTRYPOINT ["java", "-jar", "app.jar"]