JavaBank - Microservices Banking System
A modern banking system built with Spring Boot microservices architecture, featuring user management, account services, and transaction processing.

🏗️ Architecture
This project implements a microservices architecture with the following components:

Eureka Server — Service discovery and registry

User Service — User management and authentication

Account Service — Account management and operations

Transaction Service — Transaction processing (in development)

📋 Prerequisites
Before running the application, ensure you have the following installed and configured:

Java 17 or higher

Maven 3.6+

Docker and Docker Compose (for infrastructure components)

PostgreSQL (for User Service database)

MongoDB (for Account Service database)

Redis (for caching in Account Service)

🚀 Quick Start
Start Infrastructure Services
Navigate to the account-service directory and start infrastructure containers:

bash
Kopiuj
Edytuj
cd account-service
docker-compose up -d
This will launch:

MongoDB on port 27017

Redis on port 6379

Set Up PostgreSQL Database
Create a PostgreSQL database named userDB with credentials:

Username: postgres

Password: 666666

Port: 5432

Start Services
Run the microservices in this order:

bash
Kopiuj
Edytuj
# Start Eureka Server
cd eureka-server
mvn spring-boot:run
Eureka dashboard available at: http://localhost:8761

bash
Kopiuj
Edytuj
# Start User Service
cd user-service
mvn spring-boot:run
User Service runs on port 8082

bash
Kopiuj
Edytuj
# Start Account Service
cd account-service
mvn spring-boot:run
Account Service runs on port 8081

bash
Kopiuj
Edytuj
# Start Transaction Service (in development)
cd transaction-service
mvn spring-boot:run
Transaction Service runs on port 8083

📡 API Endpoints
User Service (Port 8082)
Authentication

POST /api/users/register — Register new user

POST /api/users/login — User login (returns JWT token)

User Management

GET /api/users — Get all users

GET /api/users/{username} — Get user by username

POST /api/users/updatedUser — Update user information

GET /api/users/{username}/accounts — Get user with associated accounts

Account Service (Port 8081)
GET /api/accounts — Get all accounts

GET /api/accounts/{userId} — Get accounts by user ID

GET /api/accounts/number/{accountNumber} — Get account by account number

POST /api/accounts — Create new account

GET /api/accounts/user/{userId} — Get accounts by user ID (alternative endpoint)

🛠️ Technology Stack
Backend
Spring Boot 3.2.0 — Core framework

Spring Cloud 2023.0.0 — Microservices infrastructure

Spring Security — Authentication and authorization

JWT — Token-based authentication

Netflix Eureka — Service discovery and registry

OpenFeign — Inter-service communication

Spring Data JPA — PostgreSQL database integration

Spring Data MongoDB — MongoDB integration

MapStruct — Object mapping between DTOs and entities

Lombok — Boilerplate reduction

Databases
PostgreSQL — User data persistence

MongoDB — Account data persistence

Redis — Cache layer to improve performance

Documentation
SpringDoc OpenAPI — API documentation generation

Swagger UI — Interactive API documentation interface

🔧 Configuration
Important Configuration Files
eureka-server/src/main/resources/application.yml — Eureka server settings

user-service/src/main/resources/application.yml — User Service config (DB, JWT, security)

account-service/src/main/resources/application.yml — Account Service config (MongoDB, Redis)

account-service/docker-compose.yml — Infrastructure services definitions (MongoDB, Redis)

Environment Variables
Make sure to configure the following environment variables (or in application.yml):

yaml
Kopiuj
Edytuj
jwt:
  secret: moja-super-tajna-wartosc
Database connections and other settings are preconfigured in the respective application.yml files.

📖 API Documentation
Once the services are running, access API docs here:

User Service: http://localhost:8082/swagger-ui.html

Account Service: http://localhost:8081/swagger-ui.html

🐳 Docker Support
To start required infrastructure services (MongoDB, Redis) for Account Service:

bash
Kopiuj
Edytuj
cd account-service
docker-compose up -d
🔐 Security
JWT-based authentication for all secured endpoints

Passwords encrypted using BCrypt

Service-to-service communication secured via Eureka discovery

Input validation and exception handling on all endpoints

🚧 Development Status
✅ User Service — Complete

✅ Account Service — Complete

✅ Eureka Server — Complete

🚧 Transaction Service — Under active development

🤝 Contributing
Fork the repository

Create your feature branch (git checkout -b feature/YourFeature)

Commit your changes (git commit -m 'Add feature')

Push to your branch (git push origin feature/YourFeature)

Open a Pull Request



