
---

# E-Commerce Product Management System with Payment Integration

This project is a simple E-Commerce Product Management System built using Spring Boot. It includes product and customer management, order placement, and integration with the Stripe payment gateway for order payments.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Database Setup](#database-setup)
- [API Endpoints](#api-endpoints)
- [Payment Integration](#payment-integration)
- [Security Configuration](#security-configuration)
- [Testing](#testing)
- [Documentation](#documentation)
- [Contributing](#contributing)

## Features

- **Product Management**: Create, update, view, and delete products (Admin only).
- **Customer Management**: Register new customers, update customer details.
- **Order Management**: Place orders, view orders by customer.
- **Payment Processing**: Stripe integration to handle payments for orders.
- **Security**: Basic authentication with role-based access control (Admin, User).

## Technologies Used

- Spring Boot
- Spring Data JPA
- Spring Security
- H2 Database (in-memory)
- Stripe Payment Integration
- Lombok (for reducing boilerplate code)
- JUnit and Mockito (for testing)
- Swagger (for API documentation)

## Getting Started

### Prerequisites

- Java 11 or later
- Maven
- Stripe account (for payment integration)

### Installation Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/
   cd ecommerce-product-management
   ```

2. Configure the Stripe API Key:
   - Sign up for a Stripe account and get your **secret key**.
   - Add the secret key to your `application.properties`:
     ```properties
     stripe.secret.key=your-stripe-secret-key
     ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the project:
   ```bash
   mvn spring-boot:run
   ```

5. The application will be running on `http://localhost:8080`.

## Database Setup

This project uses H2 as the in-memory database. When you run the application, the database is automatically initialized with tables and sample data using `schema.sql` and `data.sql`.

You can access the H2 console at `http://localhost:8080/h2-console` using the following credentials:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: 

## API Endpoints

Here’s a detailed explanation of the API endpoints available in this project.

### Product API (`/api/products`)

| HTTP Method | Endpoint               | Description                                   | Role         |
|-------------|------------------------|-----------------------------------------------|--------------|
| `POST`      | `/api/products`        | Create a new product                          | Admin        |
| `GET`       | `/api/products`        | Get all products                              | Public       |
| `GET`       | `/api/products/{id}`   | Get a product by its ID                       | Public       |
| `PUT`       | `/api/products/`       | Update a product                              | Admin        |
| `DELETE`    | `/api/products/{id}`   | Delete a product by its ID                    | Admin        |

### Customer API (`/api/customers`)

| HTTP Method | Endpoint                      | Description                                   | Role         |
|-------------|-------------------------------|-----------------------------------------------|--------------|
| `POST`      | `/api/customers/register`      | Register a new customer                       | Public       |
| `GET`       | `/api/customers/{id}`          | Get customer details by ID                    | User/Admin   |
| `PUT`       | `/api/customers/{id}`          | Update customer details                       | User/Admin   |

### Order API (`/api/orders`)

| HTTP Method | Endpoint                         | Description                                   | Role         |
|-------------|----------------------------------|-----------------------------------------------|--------------|
| `POST`      | `/api/orders`                    | Place a new order                          | User/Admin   |
| `GET`       | `/api/orders/customer/{customerId}` | Get all orders for a customer              | User/Admin   |
| `POST`      | `/api/orders/pay/{orderId}`       | Process payment for an order                  | User/Admin   |

### Example Requests

#### Create a New Product (Admin Only)
```http
POST /api/products/admin
Content-Type: application/json

{
  "name": "Laptop",
  "description": "A powerful gaming laptop",
  "price": 1200.00,
  "stockQuantity": 10
}
```

#### Register a Customer
```http
POST /api/customers/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "address": "123 Main St",
  "phoneNumber": "1234567890"
}
```

#### Place an Order
```http
POST /api/orders
```

#### Process Payment for an Order
```http
POST /api/orders/pay/1
Content-Type: application/x-www-form-urlencoded

stripeToken=tok_visa
```

## Payment Integration

The project integrates with **Stripe** for handling payments. To process payments:
1. Customers place an order.
2. After placing an order, call the `/api/orders/pay/{orderId}` endpoint with a valid Stripe token (e.g., `tok_visa` for testing).
3. Stripe token can be generated `/api/genrateToken`
4. The payment is processed, and if successful, the order status is updated to `PAID`.

Make sure to use your **Stripe secret key** in the `application.properties` for real-world payment processing.

### Stripe Test Cards
For testing, you can use the following **Stripe test card**:
- **Card Number**: `4242 4242 4242 4242`
- **Expiry Date**: Any valid future date
- **CVC**: Any 3-digit number

## Security Configuration

The application uses **Spring Security** to secure the API endpoints.

### Authentication:
- **Basic Authentication** is used.
- **In-memory users**:
  - Admin credentials(`/api/signup/admin`):
    - Username: `admin`
    - Password: `adminpass`
  - User credentials(`/api/signup/user`):
    - Username: `user`
    - Password: `userpass`

### Role-based Access Control:
- Admins can manage products.
- Users and Admins can place orders and manage customer profiles.

You can modify the security settings and user roles in the `SecurityConfig.java` file.

## Testing

### Unit Tests
Unit tests for services are written using **JUnit** and **Mockito**. You can run the tests using:
```bash
mvn test
```

### API Testing
Use **Postman** or **cURL** to test the REST APIs. Make sure to include the appropriate **Basic Auth credentials** when accessing protected endpoints.

## Documentation

The project includes **Swagger** for API documentation. After running the application, you can access the Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

This UI provides detailed information on all the available API endpoints, along with request/response examples.

## Contributing

If you want to contribute to this project:
1. Fork the repository.
2. Create a new feature branch.
3. Commit your changes.
4. Open a pull request.

Please ensure all new features or bug fixes are covered by unit tests.

---

This README provides detailed information on setting up and running the E-Commerce Product Management System. Let me know if you'd like to adjust or add any sections!