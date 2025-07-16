# 💳 JavaBank – Microservices Banking System

A modular banking system built using **Spring Boot** and **Spring Cloud**, implementing a microservices architecture with service discovery, RESTful APIs, and distributed components.

> This project is in active development and demonstrates how to structure a scalable microservices-based backend in Java.

---

## 📦 Project Structure

This repository consists of the following microservices and components:

| Module                      | Description                                                        |
|-----------------------------|--------------------------------------------------------------------|
| [`eureka-server`](./eureka-server)            | Eureka Service Discovery server                                 |
| [`user-service`](./user-service)              | Handles user registration, login (JWT), and profile management |
| [`account-service`](./account-service)        | Manages user bank accounts (CRUD operations, MongoDB, Redis)   |
| [`transaction-service`](./transaction-service)| (WIP) Processes account transactions and communication          |

---

## 🧰 Tech Stack

### ✅ Backend

- **Spring Boot 3.2.0**
- **Spring Cloud 2023.0.0**
- **Spring Security** + **JWT**
- **Spring Data JPA** (PostgreSQL)
- **Spring Data MongoDB** (Account storage)
- **Redis** (Caching)
- **Eureka Discovery** (Netflix)
- **OpenFeign** (Inter-service communication)
- **Resilience4j** (Circuit breakers, retry)
- **MapStruct**, **Lombok**, **ModelMapper**

### ☁️ Infrastructure

- **PostgreSQL** (User data)
- **MongoDB** (Account data)
- **Redis** (Caching)
- **Docker Compose** (for MongoDB & Redis)

---

## 🧪 Prerequisites

Ensure you have the following installed locally:

- Java 17+
- Maven 3.6+
- Docker & Docker Compose
- PostgreSQL running locally or in a container

---

## 🚀 Quick Start

### 1. Start MongoDB and Redis (for Account Service)

```bash
cd account-service
docker-compose up -d
This will start:

MongoDB: localhost:27017

Redis: localhost:6379

2. Create PostgreSQL Database for User Service
Create the database manually or use Docker:

Manual Setup:
Database: userDB

Username: postgres

Password: 666666

Port: 5432

Docker Setup:
bash
Kopiuj
Edytuj
docker run --name postgres \
  -e POSTGRES_DB=userDB \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=666666 \
  -p 5432:5432 -d postgres
3. Run the Microservices
Recommended startup order:

🧭 Start Eureka Server
bash
Kopiuj
Edytuj
cd eureka-server
mvn spring-boot:run
URL: http://localhost:8761

👤 Start User Service
bash
Kopiuj
Edytuj
cd ../user-service
mvn spring-boot:run
Runs on: http://localhost:8082

💼 Start Account Service
bash
Kopiuj
Edytuj
cd ../account-service
mvn spring-boot:run
Runs on: http://localhost:8081

💸 (Optional) Start Transaction Service
bash
Kopiuj
Edytuj
cd ../transaction-service
mvn spring-boot:run
Runs on: http://localhost:8083

📡 API Endpoints
🔐 User Service (Port 8082)
Authentication

POST /api/users/register – Register a new user

POST /api/users/login – Authenticate a user (returns JWT)

User Management

GET /api/users – Retrieve all users

GET /api/users/{username} – Get user details by username

POST /api/users/updatedUser – Update user profile

GET /api/users/{username}/accounts – Retrieve user's associated accounts

🏦 Account Service (Port 8081)
GET /api/accounts – Get all accounts

GET /api/accounts/{userId} – Get accounts by user ID

GET /api/accounts/number/{accountNumber} – Get account by account number

GET /api/accounts/user/{userId} – (Alt) Get accounts by user ID

POST /api/accounts – Create a new account

📖 Swagger Documentation
Access live API documentation:

User Service: http://localhost:8082/swagger-ui.html

Account Service: http://localhost:8081/swagger-ui.html

🔐 Security
JWT-based authentication

Secure password hashing (BCrypt)

Role-based access control (user/admin)

Service-to-service security via Eureka and Feign

⚙️ Configuration
🔑 JWT
Located in user-service/src/main/resources/application.yml:

yaml
Kopiuj
Edytuj
jwt:
  secret: moja-super-tajna-wartosc
🔧 Other Key Config Files
Path	Description
eureka-server/src/main/resources/application.yml	Eureka config
user-service/src/main/resources/application.yml	DB, JWT, port, discovery
account-service/src/main/resources/application.yml	MongoDB, Redis config
account-service/docker-compose.yml	MongoDB and Redis containers

🐳 Docker Support
To start supporting services via Docker:

bash
Kopiuj
Edytuj
cd account-service
docker-compose up -d
You can also run PostgreSQL using the Docker command shown earlier.

🚧 Development Status
Service	Status
User Service	✅ Complete
Account Service	✅ Complete
Eureka Server	✅ Complete
Transaction Service	🚧 In Progress

