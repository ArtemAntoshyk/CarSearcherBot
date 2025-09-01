FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/CarSearcherBot-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
# Використовуємо PORT від Cloud Run
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
