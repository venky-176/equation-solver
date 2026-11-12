# Algebraic Equation Solver

## 1. Project Overview

The Algebraic Equation Solver is a Spring Boot REST API application that stores and evaluates algebraic equations using expression trees.

The application accepts algebraic equations in infix notation, converts them into postfix notation, constructs binary expression trees, and stores them in memory.

Users can retrieve stored equations and evaluate them by providing values for the required variables.

## 2. Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.0.9 | Backend application framework |
| Spring Web | REST API development |
| Maven | Dependency and build management |
| JUnit 5 | Unit testing |
| MockMvc | Controller testing |
| Swagger / OpenAPI | API documentation |
| Postman | API testing |
| ConcurrentHashMap | In-memory storage |

## 3. Project Architecture

The application follows a layered architecture:

- **Controller:** Handles HTTP requests and responses.
- **Service:** Implements equation storage, retrieval, and evaluation logic.
- **Repository:** Maintains equations and expression trees in memory.
- **Model:** Contains equation and expression tree representations.
- **Utility:** Implements tokenization, infix-to-postfix conversion, tree construction, evaluation, and reconstruction.
- **DTO:** Defines request and response objects.
- **Config:** Contains OpenAPI configuration.

## 4. Expression Processing

The application processes equations through the following steps:

1. **Tokenization:** Splits the input equation into individual tokens.
2. **Infix-to-Postfix Conversion:** Converts the token sequence into postfix notation using the Shunting Yard algorithm.
3. **Expression Tree Construction:** Builds a binary expression tree from postfix notation using a stack.
4. **Storage:** Stores the original equation and its corresponding expression tree in memory.
5. **Evaluation:** Substitutes variable values and evaluates the expression tree recursively.
6. **Retrieval:** Returns the original equation text and its associated ID.

### Example

Input:

`3x + 2y - z`

Postfix representation:

`3 x * 2 y * + z -`

Expression tree:

The tree represents multiplication of 3 and x, multiplication of 2 and y, addition of those results, and subtraction of z.

For x = 2, y = 3, and z = 1:

`3(2) + 2(3) - 1 = 11`

## 5. REST API Endpoints

### A. Store an Equation

**Endpoint:** `POST /api/equations/store`

Request:

```json
{
  "equation": "3x + 2y - z"
}
```

Successful response — HTTP 201:

```json
{
  "message": "Equation stored successfully",
  "equationId": "1"
}
```

### B. Retrieve All Equations

**Endpoint:** `GET /api/equations`

Successful response — HTTP 200:

```json
{
  "equations": [
    {
      "equationId": "1",
      "equation": "3x + 2y - z"
    }
  ]
}
```

### C. Evaluate an Equation

**Endpoint:** `POST /api/equations/{equationId}/evaluate`

Request:

```json
{
  "variables": {
    "x": 2,
    "y": 3,
    "z": 1
  }
}
```

Successful response — HTTP 200:

```json
{
  "equationId": "1",
  "equation": "3x + 2y - z",
  "variables": {
    "x": 2,
    "y": 3,
    "z": 1
  },
  "result": 11.0
}
```

## 6. Error Handling

The application handles common errors:

| HTTP Status | Description |
|---|---|
| 400 Bad Request | Empty or invalid equation |
| 400 Bad Request | Missing variable values |
| 400 Bad Request | Division by zero |
| 404 Not Found | Equation ID does not exist |

A global exception handler provides structured error responses.

## 7. Running the Application

### Prerequisites

- Java JDK 21
- Maven wrapper
- IntelliJ IDEA or another Java IDE
- Postman (optional)

### Clone the repository

```bash
git clone <your-github-repository-url>
cd equation-solver
```

### Run the application

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs at:

`http://localhost:8080`

## 8. API Documentation

Swagger UI:

`http://localhost:8080/swagger-ui.html`

OpenAPI specification:

`http://localhost:8080/v3/api-docs`

Swagger UI allows users to explore and execute the available REST API endpoints.

## 9. Running Tests

Execute all tests using:

```powershell
.\mvnw.cmd clean test
```

The project includes tests for:

- Tokenization
- Infix-to-postfix conversion
- Expression tree construction
- Expression evaluation
- Expression reconstruction
- Equation storage and retrieval
- Missing variables and invalid equation IDs
- REST API controller operations

## 10. Storage

The application uses in-memory storage implemented with `ConcurrentHashMap`.

No external database is required.

**Note:** All stored equations are lost when the application restarts.

## 11. Future Enhancements

- Support for additional mathematical operations.
- Improved expression validation.
- Persistent database storage.
- Enhanced API error documentation.
- Additional integration and edge-case tests.