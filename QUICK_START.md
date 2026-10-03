# Quick Start Guide

## Prerequisites
- Docker and Docker Compose (for full functionality with Kafka and databases)
- Java 17+
- Maven 3.8+

## Option 1: Run with Docker Compose (Recommended)
1. Start infrastructure services:
   ```bash
   cd infra
   docker-compose up -d
   ```
2. Wait for all services to be healthy (Kafka, PostgreSQL, MongoDB should be ready)
3. Build and start each microservice:
   ```bash
   # In each service directory
   ../mvnw clean install
   ../mvnw spring-boot:run
   ```
   Services will be available at:
   - Auth Service: http://localhost:8081
   - Transaction Service: http://localhost:8082
   - Notification Service: http://localhost:8083
   - Reporting Service: http://localhost:8084

## Option 2: Run with Embedded Databases (For quick testing without Docker)
If you don't have Docker, you can change the database configurations to use embedded H2 databases:

### For each service, update `src/main/resources/application.properties`:

**Auth Service:**
```
spring.datasource.url=jdbc:h2:mem:authdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

**Transaction Service:**
```
spring.datasource.url=jdbc:h2:mem:transactiondb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

**Reporting Service:**
```
spring.datasource.url=jdbc:h2:mem:reportingdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-plugin=org.hibernate.dialect.H2Dialect
```

**Notification Service:** Already uses MongoDB; you can change to an embedded MongoDB or mock it for testing.

### Then run each service:
```bash
cd <service-dir>
../mvnw spring-boot:run
```

## Option 3: Run Services Separately (If you have external Kafka and databases)
Ensure you have:
- Kafka running on localhost:9092
- PostgreSQL instances on localhost:5432 (auth), 5433 (transaction), 5434 (reporting)
- MongoDB running on localhost:27017

Then build and run each service as above.

## API Endpoints

### Auth Service
- `POST /api/users/register` - Register a new user
- `GET /api/users/{username}` - Get user by username

### Transaction Service
- `POST /api/transactions` - Initiate a new transaction
- `POST /api/transactions/{id}/confirm` - Confirm a transaction
- `POST /api/transactions/{id}/fail` - Mark transaction as failed

### Notification Service
- `POST /api/notifications` - Send a notification

### Reporting Service
- `GET /api/reports/summary` - Get transaction summary (placeholder)

## Demonstrating Features

### Saga Pattern
1. Initiate a transaction via `POST /api/transactions`
2. Observe logs: Transaction service publishes `TransactionInitiated` event
3. Notification and reporting services consume the event when confirmed
4. If you simulate insufficient funds (modify code), you'll see `TransactionFailed` event

### Resilience (Resilience4j)
- Circuit breaker: Temporarily block downstream calls to see fast-failing behavior
- Retry: Kill Kafka temporarily, then restart to see events processed after retries
- Bulkhead: Generate concurrent load to see thread pool limits

### Observability
- Check logs for correlation IDs (X-Correlation-ID header) propagated across services
- Access metrics at `http://localhost:8082/actuator/prometheus` (for transaction service)
- Health checks at `http://localhost:8082/actuator/health`

## Stopping Services
Press `Ctrl+C` in each service's terminal to stop it.
To stop Docker infrastructure: `cd infra && docker-compose down`

## Troubleshooting
- If port conflicts occur, change the port in `application.properties`
- Ensure Kafka is fully started before starting services (wait for broker ready log)
- For MongoDB connection issues, verify MongoDB is running on port 27017

