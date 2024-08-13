# Employee Microservice

## Overview

- **Employee Service**: Manages employee information and CRUD operations.

Employee Service run on port 8080 and it communicate with Department Service via Feign Client.

## Technologies

- **Spring Boot**: Framework for building Java applications.
- **Hibernate**: ORM framework for database interactions.
- **H2 Database**: In-memory database for development and testing.
- **Feign Client**: For declarative REST client communication.

## Services

- **GET /employees**: Retrieve all employees.
- **GET /employees/{id}**: Retrieve a specific employee by ID.
- **POST /employees**: Add a new employee.
- **PUT /employees/{id}**: Update an existing employee by ID.
- **DELETE /employees/{id}**: Delete an employee by ID.

## Setup

### Prerequisites

- Java 17 or higher
- Maven
- Git (for version control)

### Cloning the Repository

```bash
git clone https://github.com/sezinsakl/microservice/employee-service.git
