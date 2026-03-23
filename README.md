# 💳 ApiLucaBank

**Secure • Scalable • Layered Architecture REST API**

ApiLucaBank is a secure and extensible banking API built with **Java 21** and **Spring Boot**, following a **layered architecture** approach with clean code principles and modern backend best practices.

The project is already structured with security, validation, testing, and Docker support, and can be started easily using Docker Compose.

---

# 🚀 Tech Stack

| Technology                  | Description                    |
|-----------------------------|--------------------------------|
| Java 21                     | Latest LTS version             |
| Maven                       | Dependency management          |
| Spring Boot                 | Application framework          |
| Spring Security             | Authentication & Authorization |
| JWT                         | Stateless authentication       |
| Exceptions                  | Custom Exceptions              |
| MapStructor                 | Implementation in Users        |
| Actuator                    | Defalt                         |
| Phrometheus                 | Metrics                        |
| Spring Data JPA (Hibernate) | ORM                            |
| PostgreSQL                  | Relational database            |
| Bean Validation (@Valid)    | DTO validation                 |
| Swagger / OpenAPI           | API documentation              |
| Docker                      | Containerization               |
| Docker Compose              | Multi-container setup          |
| Mockito                     | Unit testing                   |

---

# 🏗 Architecture

The project follows a **layered architecture**, with clear separation of responsibilities:


## 📂 Layers Overview

### 🔹 Controller
Handles HTTP requests and responses.

### 🔹 Service
Contains business logic and application rules.

### 🔹 Repository
Handles data access using Spring Data JPA.

### 🔹 Security
JWT authentication, authorization rules, and filters.

### 🔹 Exception
Centralized error handling using `@RestControllerAdvice`.

### 🔹 DTO
Validated request and response objects.

---

## ✅ Architecture Characteristics

- Clean and readable code
- Low coupling and good separation of concerns
- Easy to maintain and extend
- Production-oriented structure

> ⚠️ Note: The project uses a **layered architecture with clean code practices**

---

# 🔐 Security

Security is one of the main focuses of the project.

## ✔ Implemented Features

- JWT-based authentication
- Role-based access control
- Authority-based permission system
- Password encryption
- Custom authentication handling
- CORS configuration
- Input validation
- Basic logging

---

# 👥 Roles

- `USER`
- `ADMIN`
- `ENTERPRISE`

---

# 🔑 Authorities (Examples)

- `MANAGE_USER`
- `ACCOUNT`
- `SEND_TRANSACTIONS`
- `CANCEL_TRANSACTION`
- `CHAT_USER`
- `CHAT_ENTERPRISE`

---

# ✅ DTO Validation

All incoming requests are validated using **Bean Validation**.

Example:

```java
@Size(min = 2, max = 100)
@NotBlank
private String lastName;


{
  "timestamp": "2026-02-28T00:00:00Z",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input",
  "path": "/auth/register",
  "details": {
    "lastName": "Last name must be between 2 and 100 characters"
  }
}
