# Product Management API

This is a backend API built using Java and Spring Boot that allows users to manage products. The API supports creating, fetching, updating, and deleting products.

## Features

- Create new products
- Fetch all products
- Fetch a product by ID
- Update an existing product
- Delete a product

## Prerequisites

- Java 8 or higher
- Maven
- MySQL database

## Getting Started

1. Clone the repository:

   ```
   git clone https://github.com/mugosimon/product.git
   ```

2. Navigate to the project directory:

   ```
   cd product
   ```

3. Update the database connection details in the `application.properties` file:

   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. Build the project using Maven:

   ```
   mvn clean install
   ```

5. Run the application:

   ```
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080/api/v1/products`.

## Endpoints

- `POST /api/v1/products`: Create a new product
- `GET /api/v1/products`: Fetch all products
- `GET /api/v1/products/{id}`: Fetch a product by ID
- `PUT /api/v1/products/{id}`: Update an existing product
- `DELETE /api/v1/products/{id}`: Delete a product

## Swagger Documentation

The API documentation is available at `http://localhost:8080/swagger-ui.html`.

## Dependencies

The project uses the following dependencies:

- `spring-boot-starter-data-jpa`: For database integration and ORM
- `spring-boot-starter-web`: For building the web application
- `spring-boot-starter-security`: For securing the application
- `mysql-connector-java`: For connecting to the MySQL database
- `javax.servlet-api`: For providing the servlet API
- `lombok`: For generating boilerplate code
- `spring-boot-starter-validation`: For input validation
- `springfox-boot-starter`: For generating Swagger documentation
- `springfox-swagger-ui`: For the Swagger UI

## License

This project is licensed under the [MIT License](LICENSE).
