# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Smart Budget Services** is a Java microservices application with two Spring Boot services:

- **auth-service** (port 8080) — user registration, login, and JWT issuance
- **user-service** (port 8081) — user profile management, protected by JWT validation

Both services share a single PostgreSQL 17 instance but each uses its own database (`auth_service_db`, `user_service_db`).

## Build & Test Commands

Each service has its own Maven wrapper. Run commands from within the service directory.

```bash
# Build
cd auth-service && ./mvnw clean package
cd user-service && ./mvnw clean package

# Build without tests (faster, used in Docker)
./mvnw clean package -DskipTests

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=AuthControllerTest

# Run a single test method
./mvnw test -Dtest=AuthControllerTest#methodName
```

## Running Locally

**With Docker Compose** (recommended — starts Postgres + both services):
```bash
docker compose up --build
```

**Without Docker** (requires local Postgres):
- auth-service connects to `localhost:5432/auth_service_db` (credentials in `auth-service/src/main/resources/application.yml`)
- user-service connects to `localhost:5432/user_service_db`
- Run `init-db.sql` once to create the databases

## Architecture

### Authentication Flow
1. Client registers/logs in via **auth-service** (`POST /api/auth/register`, `POST /api/auth/login`)
2. auth-service returns a JWT token containing `userId` and `email` claims (1-hour expiry)
3. Client sends the JWT as `Authorization: Bearer <token>` to **user-service**
4. user-service's `JwtAuthFilter` validates the token and injects the `userId` as an `X-User-Id` header, which controllers read to identify the caller

### Service Boundaries
- `auth-service` owns `User` (credentials) — id, username, email, hashed password
- `user-service` owns `UserProfile` (profile data) — id, authUserId (FK to auth-service user), username, email, balance
- Services do **not** call each other at runtime; the JWT carries cross-service identity

### Key Patterns
- **JWT library**: JJWT 0.12.5 — `JwtService` in each service handles token operations (auth-service generates, user-service validates). **Both services hardcode the identical secret string** — if you change it in one, auth silently breaks in the other.
- **`X-User-Id` header**: Synthesized by `JwtAuthFilter` via `HttpServletRequestWrapper`; it is never trusted from the client. Controllers read it to get the caller's identity.
- **Response envelope**: All REST responses use `ApiResponse<T>` wrapper; errors use `ApiError`, handled by `GlobalExceptionHandler`
- **Security**: Spring Security with BCrypt password hashing; CORS allows `http://localhost:3000`; CSRF disabled
- **ORM**: JPA/Hibernate with `ddl-auto: update` — schema is managed automatically, no migrations

### Known Issues (from initial setup)
- JWT secret is hardcoded in both `JwtService` classes; should be a shared environment variable
- `user-service/Dockerfile` exposes port 8080 and references the auth-service JAR — needs correction to port 8081 and the user-service JAR
- Local `application.yml` files contain hardcoded DB credentials; Docker Compose overrides these via environment variables
