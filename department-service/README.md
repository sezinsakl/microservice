# Department Microservice

## Overview

- **Department Service**: Manages department information and CRUD operations.

You can acces this service via feign client like employee service.
This servisce runs on port 8081

## Technologies

- **Spring Boot**: Framework for building Java applications.
- **Hibernate**: ORM framework for database interactions.
- **H2 Database**: In-memory database for development and testing.
- **Feign Client**: For declarative REST client communication.

## Services

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
git clone https://github.com/sezinsakl/microservice/department-service.git
