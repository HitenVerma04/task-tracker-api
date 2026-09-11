# task-tracker-api

[![CI](https://github.com/HitenVerma04/task-tracker-api/actions/workflows/ci.yml/badge.svg)](https://github.com/HitenVerma04/task-tracker-api/actions/workflows/ci.yml)

REST API for managing tasks — built with Spring Boot, JPA, and PostgreSQL. Includes CRUD operations, and will expand with Redis caching, JWT auth, and CI/CD.

## v1 — CRUD API

### Stack
- Java 17, Spring Boot 3.3
- Spring Web, Spring Data JPA, Bean Validation
- H2 (default, in-memory, zero setup) / PostgreSQL (`postgres` profile)
- Lombok

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
Override connection details via `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` env vars if needed.

### Endpoints

| Method | Path                  | Description                    |
|--------|-----------------------|---------------------------------|
| POST   | `/api/v1/tasks`       | Create a task                  |
| GET    | `/api/v1/tasks`       | List tasks (optional `?status=TODO\|IN_PROGRESS\|DONE`) |
| GET    | `/api/v1/tasks/{id}`  | Get a task by id               |
| PUT    | `/api/v1/tasks/{id}`  | Update a task                  |
| DELETE | `/api/v1/tasks/{id}`  | Delete a task                  |

### Example
```bash
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Write v1 README", "description": "Document endpoints", "dueDate": "2026-08-15"}'
```

### Task shape
```json
{
  "id": 1,
  "title": "Write v1 README",
  "description": "Document endpoints",
  "status": "TODO",
  "dueDate": "2026-08-15",
  "createdAt": "2026-08-07T10:00:00Z",
  "updatedAt": "2026-08-07T10:00:00Z"
}
```

Validation errors return `400` with a `fieldErrors` map; missing tasks return `404`.

### Roadmap
- [ ] Redis caching
- [ ] JWT auth
- [x] CI/CD (GitHub Actions) — runs `mvn clean verify` on every push/PR to `main`
