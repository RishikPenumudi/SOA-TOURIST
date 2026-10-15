# Tourist Token Validation

Simple Spring Boot + PostgreSQL project structured for STS.

## Packages
- `soa` — application entry point
- `soa.controller` — REST endpoints and token check
- `soa.models` — Tourist model
- `soa.repo` — database queries
- `soa.service` — business logic and token validation

## Setup
1. Create a PostgreSQL database named `touristdb`.
2. Run the `CREATE TABLE` statement in `database.sql` while connected to `touristdb`.
3. Set your PostgreSQL password in `src/main/resources/application.properties`.
4. Import the folder in STS using **File → Import → Maven → Existing Maven Projects**.
5. Run `TouristTokenValidationApplication.java` as Spring Boot App.

Demo token: `valid123`

## API
All `/tourists` endpoints require header `Token: valid123`.

| Method | URL | Action |
|---|---|---|
| POST | `/tourists` | Add tourist |
| GET | `/tourists` | List tourists |
| GET | `/tourists/{id}` | Get tourist |
| PUT | `/tourists/{id}` | Update tourist |
| DELETE | `/tourists/{id}` | Delete tourist |

POST/PUT JSON:
```json
{"name":"Rishik","location":"Vijayawada","country":"India"}
```

A missing or invalid token returns HTTP `401 Unauthorized`.
This fixed token is for coursework demonstration only, not production authentication.
