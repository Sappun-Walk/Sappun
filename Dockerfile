FROM openjdk:17
COPY build/libs/Sappun-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/static /app/static
ENTRYPOINT ["java", "-jar", "app.jar"]
