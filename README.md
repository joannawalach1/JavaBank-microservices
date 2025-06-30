# JavaBank - Microservices Banking System

A modern banking system built with Spring Boot microservices architecture, featuring user management, account services, and transaction processing.

---

## 🏗️ Architecture

This project implements a microservices architecture with the following components:

- **Eureka Server** - Service discovery and registry  
- **User Service** - User management and authentication  
- **Account Service** - Account management and operations  
- **Transaction Service** - Transaction processing (in development)  

---

## 📋 Prerequisites

Before running the application, ensure you have the following installed:

- Java 17 or higher  
- Maven 3.6+  
- Docker and Docker Compose  
- PostgreSQL (for user service)  
- MongoDB (for account service)  
- Redis (for caching)  
- RabbitMQ (for messaging)  

---

## 🚀 Quick Start

### Start Infrastructure Services

Navigate to the `account-service` directory and start the required infrastructure:

```bash
cd account-service
docker-compose up -d
```

This will start:  
- MongoDB on port 27017  
- Redis on port 6379  
- RabbitMQ on ports 5672 (AMQP) and 15672 (Management UI)  

### Set up PostgreSQL Database

Create a PostgreSQL database named `userDB` with the following credentials:

- **Username:** `postgres`  
- **Password:** `666666`  
- **Port:** `5432`  

### Start Services

Start the services in the following order:

```bash
# Start Eureka Server
cd eureka-server
mvn spring-boot:run
```

Eureka Dashboard will be available at: [http://localhost:8761](http://localhost:8761)

```bash
# Start User Service
cd user-service
mvn spring-boot:run
```

User Service will run on port 8082

```bash
# Start Account Service
cd account-service
mvn spring-boot:run
```

Account Service will run on port 8081

```bash
# Start Transaction Service
cd transaction-service
mvn spring-boot:run
```

Transaction Service will run on port 8083

---

## 📡 API Endpoints

### User Service (Port 8082)

**Authentication**

- `POST /api/users/register` - Register new user  
- `POST /api/users/login` - User login (returns JWT token)  

**User Management**

- `GET /api/users` - Get all users  
- `GET /api/users/{username}` - Get user by username  
- `POST /api/users/updatedUser` - Update user information  
- `GET /api/users/{username}/accounts` - Get user with associated accounts  

### Account Service (Port 8081)

- `GET /api/accounts` - Get all accounts  
- `GET /api/accounts/{userId}` - Get accounts by user ID  
- `GET /api/accounts/number/{accountNumber}` - Get account by account number  
- `POST /api/accounts` - Create new account  
- `GET /api/accounts/user/{userId}` - Get accounts by user ID (alternative endpoint)  

---

## 🛠️ Technology Stack

### Backend

- Spring Boot 3.2.0 - Main framework  
- Spring Cloud 2023.0.0 - Microservices infrastructure  
- Spring Security - Authentication and authorization  
- JWT - Token-based authentication  
- Netflix Eureka - Service discovery  
- OpenFeign - Service-to-service communication  
- Spring Data JPA - Database abstraction for PostgreSQL  
- Spring Data MongoDB - MongoDB integration  
- Spring Boot AMQP - RabbitMQ integration  
- MapStruct - Object mapping  
- Lombok - Boilerplate code reduction  

### Databases

- PostgreSQL - User data storage  
- MongoDB - Account data storage  
- Redis - Caching layer  

### Message Broker

- RabbitMQ - Asynchronous messaging  

### Documentation

- SpringDoc OpenAPI - API documentation  
- Swagger UI - Interactive API documentation  

---

## 🔧 Configuration

### Key Configuration Files

- `eureka-server/src/main/resources/application.yml` - Eureka server configuration  
- `user-service/src/main/resources/application.yml` - User service configuration  
- `account-service/src/main/resources/application.yml` - Account service configuration  
- `account-service/docker-compose.yml` - Infrastructure services  

### Environment Variables

Make sure to configure the following in your environment:

```yaml
# JWT Secret (configured in user-service application.yml)
jwt:
  secret: moja-super-tajna-wartosc
```

Database configurations are already set in `application.yml` files.

---

## 📖 API Documentation

Once the services are running, you can access the API documentation:  

- User Service: [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)  
- Account Service: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)  

---

## 🐳 Docker Support

The project includes Docker Compose configuration for infrastructure services. To start all required services:

```bash
cd account-service
docker-compose up -d
```

---

## 🔐 Security

- JWT-based authentication for secure API access  
- Password encryption using BCrypt  
- Service-to-service communication through Eureka  
- Input validation on all endpoints  

---

## 🚧 Development Status

- ✅ User Service - Complete  
- ✅ Account Service - Complete  
- ✅ Eureka Server - Complete  
- 🚧 Transaction Service - In Development  

---

## 🤝 Contributing

- Fork the repository  
- Create your feature branch (`git checkout -b feature/AmazingFeature`)  
- Commit your changes (`git commit -m 'Add some AmazingFeature'`)  
- Push to the branch (`git push origin feature/AmazingFeature`)  
- Open a Pull Request  

---

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 📞 Support

For support and questions, please open an issue in the GitHub repository.

---

**Note:** This is a educational/demonstration project showcasing microservices architecture with Spring Boot and Spring Cloud.

