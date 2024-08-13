# Employee and Department Microservices Project

## Overview

This project consists of two microservices:
- **Employee Service**: Manages employee information and CRUD operations.
- **Department Service**: Manages department information and CRUD operations.

Both services use Spring Boot and Hibernate for persistence and are integrated with H2 databases. Employee Service and Department Service communicate via Feign Client.

## Technologies

- **Spring Boot**: Framework for building Java applications.
- **Hibernate**: ORM framework for database interactions.
- **H2 Database**: In-memory database for development and testing.
- **Feign Client**: For declarative REST client communication.

## Services

### Employee Service

- **GET /employees**: Retrieve all employees.
- **GET /employees/{id}**: Retrieve a specific employee by ID.
- **POST /employees**: Add a new employee.
- **PUT /employees/{id}**: Update an existing employee by ID.
- **DELETE /employees/{id}**: Delete an employee by ID.

### Department Service

- **GET /departments**: Retrieve all departments.
- **GET /departments/{id}**: Retrieve a specific department by ID.
- **POST /departments**: Add a new department.
- **PUT /departments/{id}**: Update an existing department by ID.
- **DELETE /departments/{id}**: Delete a department by ID.

## Setup

### Prerequisites

- Java 17 or higher
- Maven
- Git (for version control)

### Cloning the Repository

```bash
git clone https://github.com/sezinsakl/employeeProject.git
