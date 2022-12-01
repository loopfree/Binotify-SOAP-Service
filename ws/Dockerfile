FROM amazoncorretto:11
FROM maven:3-amazoncorretto

WORKDIR /app
COPY . .
RUN mvn clean package
CMD ["java", "-jar", "/app/target/ws-1.0-SNAPSHOT-shaded.jar"]
