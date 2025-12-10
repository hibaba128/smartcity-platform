FROM eclipse-temurin:17-jre-alpine

# Download H2
ENV H2_VERSION="2.2.224"
RUN wget https://github.com/h2database/h2database/releases/download/version-${H2_VERSION}/h2-${H2_VERSION}.jar -O /opt/h2.jar

# Data directory
RUN mkdir -p /opt/h2-data
VOLUME /opt/h2-data

EXPOSE 8082 1521

# Start H2 Server (Web Console + TCP)
CMD ["java", "-cp", "/opt/h2.jar", "org.h2.tools.Server", \
     "-web", "-webAllowOthers", "-webPort", "8082", \
     "-tcp", "-tcpAllowOthers", "-tcpPort", "1521", \
     "-baseDir", "/opt/h2-data"]
