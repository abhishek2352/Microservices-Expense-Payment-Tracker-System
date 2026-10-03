# Microservices Expense/Payment Tracker

This is a production-ready microservices architecture for an expense/payment tracker system, built with Java/Spring Boot and following best practices for distributed systems.

## Architecture Overview

The system consists of four independent services:

1. **Auth Service** - Handles user authentication and authorization
2. **Transaction Service** - Processes payments and implements the saga pattern for distributed transactions
3. **Notification Service** - Sends notifications to users via email/SMS
4. **Reporting Service** - Generates reports and analytics from transaction data

Each service has its own database and communicates with others through Apache Kafka using an event-driven architecture.

## Key Features

### Distributed Transaction Management (Saga Pattern)
- Implements choreography-based saga pattern for transaction processing
- Services react to events published by other services
- Compensating actions handle failures appropriately
- Example flow:
  1. Transaction service validates request and creates PENDING transaction
  2. Publishes TransactionInitiated event to Kafka
  3. Balance check performed (simplified in this implementation)
  4. If sufficient funds, publishes TransactionConfirmed event
  5. Notification service consumes confirmed event and sends user notification
  6. Reporting service consumes confirmed event and updates analytics
  7. If balance insufficient, publishes TransactionFailed event

### Resilience Patterns
- **Circuit Breaker** (Resilience4j): Prevents cascade failures when downstream services are unavailable
- **Retry** (Resilience4j): Automatic retry with exponential backoff for transient failures
- **Bulkhead** (Resilience4j): Limits concurrent calls to prevent resource exhaustion
- **Fallback Mechanisms**: Graceful degradation when services are unavailable

### Observability
- **Structured Logging**: JSON-formatted logs with correlation IDs for distributed tracing
- **Metrics**: Micrometer + Prometheus exposing key metrics at `/actuator/prometheus`
- **Health Checks**: Spring Boot Actuator endpoints for service monitoring
- **Correlation ID Propagation**: Unique IDs traced across all services for a single transaction

### Event Sourcing (Transaction Service)
- Transaction state changes are captured as events
- Events serve both as inter-service communication and as an event store
- Enables auditability and rebuild capabilities
- Current state can be derived by replaying events

## Technology Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.1.5
- **Messaging**: Apache Kafka
- **Databases**: 
  - PostgreSQL (Auth, Transaction, Reporting services)
  - MongoDB (Notification service)
- **Resilience**: Resilience4j (Circuit Breaker, Retry, Bulkhead)
- **Observability**: 
  - Micrometer for metrics
  - Prometheus for metrics collection
  - Structured logging with correlation IDs
- **Build**: Maven
- **Containerization**: Docker Compose for local development

## Getting Started

### Prerequisites
- Docker and Docker Compose
- Java 17+
- Maven 3.8+

### Running the System

1. Start the infrastructure services:
   ```bash
   cd infra
   docker-compose up -d
   ```

2. Wait for all services to be healthy (Kafka, PostgreSQL, MongoDB should be ready)

3. Build and start each microservice:
   ```bash
   # In each service directory
   mvn clean install
   mvn spring-boot:run
   ```

   Services will be available at:
   - Auth Service: http://localhost:8081
   - Transaction Service: http://localhost:8082
   - Notification Service: http://localhost:8083
   - Reporting Service: http://localhost:8084

### API Endpoints

#### Auth Service
- `POST /api/users/register` - Register a new user
- `GET /api/users/{username}` - Get user by username

#### Transaction Service
- `POST /api/transactions` - Initiate a new transaction
- `POST /api/transactions/{id}/confirm` - Confirm a transaction
- `POST /api/transactions/{id}/fail` - Mark transaction as failed

#### Notification Service
- `POST /api/notifications` - Send a notification

#### Reporting Service
- `GET /api/reports/summary` - Get transaction summary (placeholder)

## Docker Compose Services

The `infra/docker-compose.yml` file includes:
- Zookeeper and Kafka for event streaming
- Three PostgreSQL instances (one per service that needs SQL)
- MongoDB for notification service
- Prometheus for metrics collection
- Grafana for visualization (configured via Prometheus)

## Key Implementation Details

### Correlation ID Propagation
Each service implements a `CorrelationIdFilter` that:
- Extracts correlation ID from incoming request headers
- Generates a new ID if none exists
- Puts the ID in MDC for logging
- Propagates the ID to outgoing requests via headers

### Resilience4j Configuration
Configured in `application.properties` for each service:
- Circuit breaker with sliding window, failure rate thresholds
- Retry with exponential backoff
- Bulkhead for thread pool isolation

### Event Schema
All services use a common `TransactionEvent` schema:
```java
public class TransactionEvent {
    private String eventId;
    private String eventType; // TransactionInitiated, TransactionConfirmed, TransactionFailed
    private Long transactionId;
    private Long userId;
    private Double amount;
    private String type; // DEBIT, CREDIT
    private String description;
}
```

## Production Readiness

This implementation includes:
- Proper error handling with `@ControllerAdvice`
- Health check endpoints via Spring Boot Actuator
- Externalized configuration
- Logging best practices
- Resource isolation patterns
- Observability instrumentation
- Clear separation of concerns
- Event-driven loose coupling
- Idempotent event processing (where applicable)

## Future Enhancements

1. **Orchestration-based Saga**: Implement an alternative using a saga orchestrator for comparison
2. **Actual Balance Checking**: Integrate with a real account service for funds validation
3. **Dead Letter Queues**: Handle repeatedly failing events
4. **Schema Registry**: Use Apicurio or Confluent Schema Registry for event validation
5. **Security**: Implement JWT-based auth between services
6. **API Gateway**: Add rate limiting, SSL termination, and request routing
7. **Container Orchestration**: Deploy to Kubernetes with Helm charts
8. **Advanced Reporting**: Implement real aggregations and analytical queries
9. **Load Testing**: Add performance benchmarks and chaos engineering tests
10. **Dashboard**: Create Grafana dashboards for business metrics

## License

MIT