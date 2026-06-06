FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ../../../.. .

RUN ./mvnw clean package

CMD ["java", "-jar", "target/product-management-0.0.1-SNAPSHOT.jar"]