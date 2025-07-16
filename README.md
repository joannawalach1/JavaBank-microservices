# JavaBank - Microservices Banking System

A modern banking system built with Spring Boot microservices architecture, featuring user management, account services, and transaction processing.

---

## 🏗️ Architecture

This project follows a microservices architecture and includes the following components:

- **Eureka Server** – Service discovery and registry  
- **User Service** – User management and authentication  
- **Account Service** – Account management and operations  
- **Transaction Service** – Transaction processing (in development)

---

## 📋 Prerequisites

Before running the application, ensure you have:

- Java 17 or higher  
- Maven 3.6+  
- Docker & Docker Compose  
- PostgreSQL (for User Service)  
- MongoDB (for Account Service)  
- Redis (for Account Service caching)

---

## 🚀 Quick Start

### 1. Start Infrastructure Services

Run the infrastructure containers (MongoDB, Redis):

```bash
cd account-service
docker-compose up -d
This launches:

MongoDB on port 27017

Redis on port 6379

RabbitMQ is not currently required.

2. Set Up PostgreSQL (User Service)
Create a PostgreSQL database:

Database: userDB

Username: postgres

Password: 666666

Port: 5432

3. Run Microservices
Start each service in this order:

bash
Kopiuj
Edytuj
# Eureka Server
cd eureka-server
mvn spring-boot:run
Eureka Dashboard

bash
Kopiuj
Edytuj
# User Service (Port 8082)
cd ../user-service
mvn spring-boot:run
bash
Kopiuj
Edytuj
# Account Service (Port 8081)
cd ../account-service
mvn spring-boot:run
bash
Kopiuj
Edytuj
# Transaction Service (Port 8083 - optional/in development)
cd ../transaction-service
mvn spring-boot:run
📡 API Endpoints
🔐 User Service
Authentication

POST /api/users/register – Register new user

POST /api/users/login – Authenticate (returns JWT)

User Management

GET /api/users – Get all users

GET /api/users/{username} – Get user by username

POST /api/users/updatedUser – Update user

GET /api/users/{username}/accounts – Get user with accounts

🧾 Account Service
GET /api/accounts – Get all accounts

GET /api/accounts/{userId} – Get accounts by user ID

GET /api/accounts/number/{accountNumber} – Get by account number

POST /api/accounts – Create new account

GET /api/accounts/user/{userId} – Alternative user ID endpoint

🛠️ Technology Stack
Backend
Spring Boot 3.2.0

Spring Cloud 2023.0.0

Spring Security + JWT

Netflix Eureka

Spring Data JPA (PostgreSQL)

Spring Data MongoDB

OpenFeign (service-to-service)

Lombok, MapStruct

Databases
PostgreSQL – Users

MongoDB – Accounts

Redis – Cache

API Docs
SpringDoc OpenAPI + Swagger UI

🔧 Configuration
Key Config Files
eureka-server/src/main/resources/application.yml

user-service/src/main/resources/application.yml

account-service/src/main/resources/application.yml

account-service/docker-compose.yml – for MongoDB & Redis

JWT Secret
Ensure user-service has:

yaml
Kopiuj
Edytuj
jwt:
  secret: moja-super-tajna-wartosc
📖 Swagger UI
User Service – Swagger

Account Service – Swagger

🐳 Docker Support
Start required services for Account Service:

bash
Kopiuj
Edytuj
cd account-service
docker-compose up -d
🔐 Security Features
JWT token-based auth

BCrypt password hashing

Secure service-to-service communication via Eureka

Input validation

🚧 Development Status
Component	Status
User Service	✅ Complete
Account Service	✅ Complete
Eureka Server	✅ Complete
Transaction Service	🚧 In Progress

