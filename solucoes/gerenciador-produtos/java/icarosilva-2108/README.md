# Product Management API

A RESTful API built with Spring Boot for managing products and categories.

This project was developed as a solution for the Product Management Challenge and emphasizes clean architecture, data validation, exception handling, automated testing, containerization, and continuous integration.
 
---

## Features

### Categories

- Create categories
- Retrieve all categories
- Delete categories
- Prevent deletion of categories that are associated with existing products
### Products

- Create products
- Retrieve all products
- Retrieve a product by ID
- Update products
- Delete products
### Filtering

Products can be filtered by:

- Category ID
- Minimum price
- Maximum price
  Filtering is implemented using Spring Data JPA Specifications, allowing dynamic query construction without the need for multiple repository methods.

---

## Technologies

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Bean Validation
- Springdoc OpenAPI (Swagger)
- Docker
- Docker Compose
- JUnit 5
- Mockito
- MockMvc
- GitHub Actions
---

## Project Structure

The application follows a layered architecture to promote separation of concerns and maintainability.

### Product Module

- Controller
- Service
- Repository
- DTOs
- Specifications
### Category Module

- Controller
- Service
- Repository
- DTOs
### Exception Handling

- Custom Exceptions
- Global Exception Handler
- Error Response DTOs
---

## Running Locally

### Requirements

- Java 21
- Maven or Maven Wrapper
- PostgreSQL

### Create a .env file
Use the .env.example file as an example
```dotenv
DB_URL=YOUR_DB_URL
DB_NAME=YOUR_DB_NAME
DB_USERNAME=YOUR_DB_USERNAME
DB_PASSWORD=YOUR_DB_PASSWORD
```
### Run the application

```bash
./mvnw spring-boot:run
```
 
---

## Running with Docker

Build and start the containers:

```bash
docker compose up --build
```

Stop the containers:

```bash
docker compose down
```
 
---

## Running Tests

```bash
./mvnw test
```
 
---

## API Documentation

Swagger UI is available after starting the application:

```
http://localhost:8080/swagger-ui.html
```
 
---

## Error Handling

The application provides standardized error responses through a global exception handler.

Handled scenarios include:

- Resource not found
- Duplicate product name
- Duplicate category name
- Attempting to delete a category associated with products
- Validation errors
- Invalid JSON request bodies
---

## Design Decisions

### DTO Separation

Request and response DTOs are separated to avoid exposing internal entity details and to provide clear API contracts.

### Validation

Bean Validation is applied at the API boundary to ensure data consistency before business rules are executed.

### Specifications

Spring Data JPA Specifications are used to implement flexible and dynamic filtering capabilities without creating multiple repository query methods.

### Testing

The project includes:

- Service layer unit tests using Mockito
- Controller tests using MockMvc
- Application context loading tests
### CI Pipeline

A GitHub Actions workflow automatically runs the test suite on pushes and pull requests, helping ensure code quality and stability.

---

## Author

Icaro Silva  
[Linkedin](https://www.linkedin.com/in/icaro-silva-10885a365)  
[GitHub](https://github.com/icaro-silva2108)