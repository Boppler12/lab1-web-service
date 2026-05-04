
# Använd en lättvikts-version av Java 25 (eftersom din pom.xml säger Java 25)
FROM eclipse-temurin:25-jdk-alpine

# Skapa en arbetsmapp
WORKDIR /app

# Kopiera den byggda JAR-filen från target-mappen till containern
COPY target/*.jar app.jar

# Starta applikationen
ENTRYPOINT ["java", "-jar", "app.jar"]