FROM openjdk:17-jdk-slim
WORKDIR /wallet_app
COPY target/wallet_app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]