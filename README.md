# JavaBank-microservices

JavaBank-microservices to przykładowa aplikacja bankowa zbudowana w architekturze mikroserwisów, wykorzystująca nowoczesne technologie JVM i ekosystem Spring. Projekt demonstruje, jak stworzyć skalowalny, modularny i łatwy w utrzymaniu system bankowy rozłożony na kilka niezależnych mikroserwisów komunikujących się asynchronicznie i synchronicznie.

---

## Spis treści

- [Opis projektu](#opis-projektu)  
- [Architektura](#architektura)  
- [Funkcjonalności](#funkcjonalności)  
- [Technologie](#technologie)  
- [Struktura projektu](#struktura-projektu)  
- [Instrukcja uruchomienia](#instrukcja-uruchomienia)  
- [Przykłady użycia](#przykłady-użycia)  
- [Monitorowanie i logowanie](#monitorowanie-i-logowanie)  
- [Rozwój projektu](#rozwój-projektu)  
- [Autor](#autor)  
- [Licencja](#licencja)  

---

## Opis projektu

JavaBank-microservices to kompletny system bankowy podzielony na niezależne mikroserwisy, które realizują wybrane domeny biznesowe, takie jak zarządzanie użytkownikami, kontami bankowymi, transakcjami i powiadomieniami. Projekt demonstruje:

- Zastosowanie Spring Boot i Spring Cloud do budowy mikroserwisów  
- Rejestrację i odnajdywanie usług przez Eureka Server  
- Bezpieczną autoryzację i uwierzytelnianie za pomocą JWT  
- Komunikację synchroniczną (REST) i asynchroniczną (RabbitMQ)  
- Integrację z bazami danych (MongoDB, Cassandra) oraz Elasticsearch  
- Monitoring za pomocą Prometheus i Grafana  
- Skalowalność i elastyczność wdrożeń dzięki Dockerowi i Docker Compose  

---

## Architektura

+----------------+ +---------------+ +------------------+
| Frontend UI | ---> | API Gateway | <---> | Eureka Server |
+----------------+ +---------------+ +------------------+
| | |
-------------------------------------------------
| | | |
+-----------+ +------------+ +--------------+ +---------------+
| user-service | account-service | transaction-service | notification-service |
+-----------+ +------------+ +--------------+ +---------------+
| | | |
MongoDB / Cassandra MongoDB MongoDB RabbitMQ (MQ)

yaml
Kopiuj
Edytuj

- **API Gateway**: Punkt wejścia do systemu, odpowiedzialny za routing, uwierzytelnianie i agregację usług  
- **Eureka Server**: Rejestracja i odnajdywanie mikroserwisów  
- **User Service**: Zarządzanie użytkownikami, profilem i rolami  
- **Account Service**: Obsługa kont bankowych, sald i limitów  
- **Transaction Service**: Przetwarzanie i historia transakcji  
- **Notification Service**: Wysyłanie powiadomień o zdarzeniach (np. potwierdzenia transakcji)  
- **RabbitMQ**: Komunikacja asynchroniczna (event-driven architecture)  

---

## Funkcjonalności

- Rejestracja, logowanie i zarządzanie użytkownikami z JWT  
- CRUD kont bankowych i kontrola statusu konta  
- Wykonywanie i rejestrowanie transakcji między kontami  
- Asynchroniczne powiadomienia i obsługa zdarzeń  
- Pełna obsługa błędów i walidacji danych  
- Monitoring metryk i logów w czasie rzeczywistym  

---

## Technologie

| Kategoria           | Technologie                        |
|---------------------|----------------------------------|
| Język programowania  | Java 11+                         |
| Framework           | Spring Boot, Spring Cloud         |
| Bazy danych         | MongoDB, Cassandra                |
| Messaging           | RabbitMQ                         |
| Rejestracja usług   | Eureka Server                    |
| Bezpieczeństwo      | JWT, Spring Security              |
| Monitoring          | Prometheus, Grafana, Elasticsearch, Kibana |
| Konteneryzacja      | Docker, Docker Compose             |
| Frontend (opcjonalny)| React                           |

---

## Struktura projektu

java-bank-microservices/
├── api-gateway/ # Gateway API - routing, autoryzacja
├── eureka-service/ # Serwer Eureka - service discovery
├── user-service/ # Mikroserwis użytkowników
├── account-service/ # Mikroserwis kont bankowych
├── transaction-service/ # Mikroserwis transakcji
├── notification-service/ # Mikroserwis powiadomień
├── frontend/ # Aplikacja React (opcjonalnie)
├── docker-compose.yml # Plik kompozycji kontenerów
└── README.md # Ten plik

yaml
Kopiuj
Edytuj

---

## Instrukcja uruchomienia

### Wymagania wstępne

- JDK 11 lub wyższy  
- Docker i Docker Compose (zalecane)  
- Maven  

### Uruchomienie lokalne

1. Sklonuj repozytorium:  
   ```bash
   git clone https://github.com/joannawalach1/JavaBank-microservices.git
   cd JavaBank-microservices
Uruchom bazę danych (MongoDB, Cassandra) oraz RabbitMQ (np. przez Dockera):

bash
Kopiuj
Edytuj
docker-compose up -d mongodb cassandra rabbitmq
Uruchom Eureka Server:

bash
Kopiuj
Edytuj
cd eureka-service
./mvnw spring-boot:run
Uruchom mikroserwisy w kolejności:

user-service
account-service
transaction-service
notification-service

Każdy uruchamiaj poleceniem:

bash
Kopiuj
Edytuj
./mvnw spring-boot:run
Uruchom API Gateway:

bash
Kopiuj
Edytuj
cd ../api-gateway
./mvnw spring-boot:run
(Opcjonalnie) Uruchom frontend React:

bash
Kopiuj
Edytuj
cd ../frontend
npm install
npm start
Uruchomienie za pomocą Dockera (cały system)
bash
Kopiuj
Edytuj
docker-compose up --build
Przykłady użycia
Rejestracja użytkownika
css
Kopiuj
Edytuj
POST /user-service/api/users/register
Body:
{
  "email": "user@example.com",
  "password": "strongpassword",
  "name": "Jan Kowalski"
}
Logowanie i pobranie tokena JWT
css
Kopiuj
Edytuj
POST /user-service/api/users/login
Body:
{
  "email": "user@example.com",
  "password": "strongpassword"
}
Response:
{
  "token": "jwt-token-string"
}
Pobranie listy kont użytkownika (z tokenem)
vbnet
Kopiuj
Edytuj
GET /account-service/api/accounts
Headers:
Authorization: Bearer <jwt-token-string>
Monitorowanie i logowanie
Prometheus zbiera metryki mikroserwisów

Grafana wizualizuje dane metryk w czasie rzeczywistym

Elasticsearch + Kibana przechowują i przeszukują logi aplikacji

Wszystkie mikroserwisy mają endpointy health check oraz metrics zgodne z Spring Actuator

Rozwój projektu
Dodanie wsparcia dla płatności zewnętrznych (np. integracja z systemem bankowym)

Rozbudowa frontendowej aplikacji React o panel administracyjny

Dodanie testów integracyjnych i end-to-end

Rozwój automatyzacji CI/CD z GitHub Actions

Skalowanie systemu w chmurze (np. Kubernetes)

Autor
Joanna Walach
GitHub: https://github.com/joannawalach1


