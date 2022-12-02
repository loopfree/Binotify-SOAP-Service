FROM amazoncorretto:11
FROM maven:3-amazoncorretto

WORKDIR /app
COPY . .
RUN mvn clean package
ENV API_KEY_PLAIN=plainkey
ENV API_KEY_REST=restkey
CMD ["java", "-jar", "/app/target/ws-1.0-SNAPSHOT.jar"]
