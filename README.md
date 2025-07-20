JavaBank Microservices

Nowoczesny system bankowy oparty na architekturze mikrousług, zbudowany z wykorzystaniem Spring Boot

JavaBank to zaawansowany system bankowy implementujący architekturę mikrousług, zapewniający bezpieczne zarządzanie użytkownikami, kontami oraz transakcjami. Projekt wykorzystuje najnowsze technologie Java i Spring Boot do stworzenia skalowalnego, niezawodnego rozwiązania bankowego.
🏗️ Architektura Systemu
Projekt składa się z następujących mikrousług:

Eureka Server - Serwer odkrywania i rejestracji usług
User Service - Zarządzanie użytkownikami i autoryzacja
Account Service - Zarządzanie kontami i operacje
Transaction Service - Przetwarzanie transakcji (w trakcie rozwoju)

🚀 Kluczowe Funkcjonalności
Zarządzanie Użytkownikami

Rejestracja i logowanie użytkowników
Bezpieczna autoryzacja z użyciem JWT tokenów
Szyfrowanie haseł za pomocą BCrypt
CRUD operacje na danych użytkowników

System Kont

Tworzenie i zarządzanie kontami bankowymi
Integracja z danymi użytkowników
Cache'owanie z wykorzystaniem Redis dla lepszej wydajności
Elastyczne wyszukiwanie kont (po ID użytkownika, numerze konta)

Bezpieczeństwo

Autoryzacja oparta na JWT tokenach
Zabezpieczona komunikacja między serwisami
Walidacja danych wejściowych
Obsługa wyjątków na wszystkich endpointach

🛠️ Stack Technologiczny
Backend Framework

Spring Boot 3.2.0 - Główny framework aplikacyjny
Spring Cloud 2023.0.0 - Infrastruktura mikrousług
Spring Security - Autoryzacja i uwierzytelnianie
Netflix Eureka - Service discovery
OpenFeign - Komunikacja między serwisami

Bazy Danych

PostgreSQL - Przechowywanie danych użytkowników
MongoDB - Przechowywanie danych kont
Redis - Warstwa cache dla lepszej wydajności

Narzędzia Pomocnicze

MapStruct - Mapowanie obiektów między DTO a encjami
Lombok - Redukcja boilerplate code
SpringDoc OpenAPI - Generowanie dokumentacji API
Docker Compose - Konteneryzacja usług infrastrukturalnych

📋 Wymagania Systemowe

Java 17+
Maven 3.6+
Docker i Docker Compose
PostgreSQL
MongoDB (uruchamiana przez Docker)
Redis (uruchamiany przez Docker)

🔧 Instalacja i Uruchomienie
1. Uruchomienie Infrastruktury
bashcd account-service
docker-compose up -d
2. Konfiguracja Bazy PostgreSQL
Utwórz bazę danych:

Nazwa: userDB
Użytkownik: postgres
Hasło: 666666
Port: 5432

3. Uruchomienie Mikrousług
Eureka Server:
bashcd eureka-server
mvn spring-boot:run
Dashboard: http://localhost:8761
User Service:
bashcd user-service
mvn spring-boot:run
Port: 8082
Account Service:
bashcd account-service
mvn spring-boot:run
Port: 8081
Transaction Service:
bashcd transaction-service
mvn spring-boot:run
Port: 8083 (w rozwoju)
📡 Endpoints API
User Service (Port 8082)
MethodEndpointOpisPOST/api/users/registerRejestracja nowego użytkownikaPOST/api/users/loginLogowanie (zwraca JWT token)GET/api/usersLista wszystkich użytkownikówGET/api/users/{username}Szczegóły użytkownikaPOST/api/users/updatedUserAktualizacja danych użytkownika
Account Service (Port 8081)
MethodEndpointOpisGET/api/accountsLista wszystkich kontPOST/api/accountsUtworzenie nowego kontaGET/api/accounts/{userId}Konta użytkownikaGET/api/accounts/number/{accountNumber}Konto po numerze
📖 Dokumentacja API
Po uruchomieniu serwisów, dokumentacja dostępna pod adresami:

User Service: http://localhost:8082/swagger-ui.html
Account Service: http://localhost:8081/swagger-ui.html

🐳 Docker Support
Projekt wykorzystuje Docker Compose do uruchamiania usług infrastrukturalnych:

MongoDB (port 27017)
Redis (port 6379)

bashcd account-service
docker-compose up -d
🔐 Konfiguracja Bezpieczeństwa
yamljwt:
  secret: moja-super-tajna-wartosc

Uwaga: Wszystkie hasła są szyfrowane przy użyciu BCrypt, a komunikacja między serwisami jest zabezpieczona przez Eureka discovery.

🚧 Status Rozwoju
SerwisStatusUser Service✅ UkończonyAccount Service✅ UkończonyEureka Server✅ UkończonyTransaction Service🚧 W trakcie rozwoju
🤝 Wkład w Projekt
Projekt jest otwarty na współpracę. W przypadku znalezienia błędów lub pomysłów na ulepszenia, prosimy o utworzenie Issue lub Pull Request.
📝 Licencja
Projekt udostępniony na zasadach licencji MIT.

Autor: joannawalach1
Repozytorium: JavaBank-microservices
