# task-tracker-api

[![CI](https://github.com/HitenVerma04/task-tracker-api/actions/workflows/ci.yml/badge.svg)](https://github.com/HitenVerma04/task-tracker-api/actions/workflows/ci.yml)

REST API for managing tasks — built with Spring Boot, Spring Data JPA, Spring Security (JWT), and PostgreSQL. Includes full CRUD operations, authentication, validation, global exception handling, and CI/CD.

## v1 — CRUD API with JWT Auth

### Stack
- Java 17, Spring Boot 3.3
- Spring Security (JWT via `jjwt` 0.12.x), Spring Web, Spring Data JPA, Bean Validation
- H2 (default, in-memory, zero setup) / PostgreSQL (`postgres` profile)
- Lombok

### Environment Variables
- `JWT_SECRET`: Base64 or 256-bit secret key used to sign JWT tokens. A default development key is provided for local execution, but `JWT_SECRET` should be overridden in production environments.
- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`: Database credentials for the `postgres` profile.

### Run it (H2, no setup needed)
```bash
./mvnw spring-boot:run
```
The `dev` profile is active by default and uses an in-memory H2 database. Console at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:taskdb`, user: `sa`, no password).

### Run it against PostgreSQL
```bash
docker compose up -d
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

### Endpoints

#### Authentication (Public)
| Method | Path                  | Description                           |
|--------|-----------------------|---------------------------------------|
| POST   | `/api/v1/auth/register` | Register a new user (`username`, `password`) |
| POST   | `/api/v1/auth/login`    | Login user & return JWT token         |

#### Tasks (Protected — Requires `Authorization: Bearer <token>`)
| Method | Path                  | Description                           |
|--------|-----------------------|---------------------------------------|
| POST   | `/api/v1/tasks`       | Create a task                         |
| GET    | `/api/v1/tasks`       | List tasks (optional `?status=TODO\|IN_PROGRESS\|DONE`) |
| GET    | `/api/v1/tasks/{id}`  | Get a task by id                      |
| PUT    | `/api/v1/tasks/{id}`  | Update a task                         |
| DELETE | `/api/v1/tasks/{id}`  | Delete a task                         |

---

### Authentication Workflow Example (cURL)

1. **Register a User**:
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "hiten", "password": "password123"}'
```

2. **Login to Get JWT Token**:
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "hiten", "password": "password123"}'
```
*Response*:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "username": "hiten",
  "role": "USER",
  "expiresInMs": 3600000
}
```

3. **Access Protected Tasks API with Bearer Token**:
```bash
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
  -H "Content-Type: application/json" \
  -d '{"title": "Implement JWT Auth", "description": "Add Spring Security and JJWT", "dueDate": "2026-09-30"}'
```

---

### Roadmap
- [ ] Redis caching
- [x] JWT authentication
- [x] CI/CD (GitHub Actions) — runs `mvn clean verify` on every push/PR to `main`
