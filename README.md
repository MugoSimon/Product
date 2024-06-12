```markdown
# Product Management Service

This is a sample Spring Boot application that demonstrates the management of products, including creation, fetching, updating, and deleting products. It also integrates with a coupon service to apply discounts to products.

## Features

- Create a product
- Fetch a product by ID
- Fetch all products
- Update a product
- Delete a product
- Apply coupon discounts to products

## Prerequisites

- Java 11 or higher
- Maven
- MySQL
- Spring Boot

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/mugosimon/product-management-service.git
   ```

2. Navigate to the project directory:

   ```bash
   cd product-management-service
   ```

3. Update the `application.properties` file in `src/main/resources` with your MySQL database credentials and the coupon service URL:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/product_db
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   couponService.url=http://localhost:7074/coupons/
   ```

4. Build the project using Maven:

   ```bash
   mvn clean install
   ```

5. Run the application:

   ```bash
   mvn spring-boot:run
   ```

## Project Structure

The project consists of the following main files and folders:

- `ProductService.java`: The service class that handles the business logic for product management.
- `ProductRepository.java`: The repository interface for CRUD operations on products.
- `Product.java`: The entity class representing a product.
- `Coupon.java`: The DTO class representing a coupon.
- `CouponResponse.java`: The DTO class representing the response from the coupon service.
- `EntityResponse.java`: A utility class for standardizing API responses.
- `application.properties`: The configuration file for the application.
- `pom.xml`: The Maven build configuration file.

## Dependencies

The project uses the following dependencies:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>

    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
        <version>3.0.4</version>
    </dependency>

    <dependency>
        <groupId>jakarta.validation</groupId>
        <artifactId>jakarta.validation-api</artifactId>
        <version>2.0.2</version>
    </dependency>

    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-boot-starter</artifactId>
        <version>3.0.0</version>
    </dependency>

    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>3.0.0</version>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Usage

1. Create a product by sending a POST request to `/products` with the product details.
2. Fetch a product by ID by sending a GET request to `/products/{id}`.
3. Fetch all products by sending a GET request to `/products`.
4. Update a product by sending a PUT request to `/products` with the updated product details.
5. Delete a product by sending a DELETE request to `/products/{id}`.

## Troubleshooting

If you encounter any issues while running the app, please make sure that:

1. Your MySQL database credentials are correctly configured in the `application.properties` file.
2. The coupon service URL is correctly configured in the `application.properties` file.
3. The necessary dependencies are included in the `pom.xml` file.
4. You have a running instance of MySQL.

## Contributing

If you find any bugs or have suggestions for improvements, please feel free to open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
```

### Steps to Create a License File

Create a file named `LICENSE` in the root directory of your project and include the following text:

```text
MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

### Updating the Repository Link

Replace the repository URL in the `README.md` file with the actual URL of your GitHub repository:

```markdown
git clone https://github.com/mugosimon/product-management-service.git
