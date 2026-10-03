package com.example.transactionservice.config;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Resilience4jConfig {

    // Circuit Breaker configuration for external service calls
    @CircuitBreaker(name = "externalService", fallbackMethod = "externalServiceFallback")
    public String callExternalService(String serviceName) {
        // This would be the actual call to an external service
        // For demo purposes, we'll just return a success message
        return "External service call successful";
    }

    public String externalServiceFallback(String serviceName, Throwable throwable) {
        // Fallback when circuit breaker is open
        return "External service is currently unavailable. Fallback response.";
    }

    // Retry configuration for Kafka consumer processing
    @Retry(name = "kafkaConsumerRetry", fallbackMethod = "kafkaConsumerFallback")
    public void processTransactionEventWithRetry(String eventData) {
        // This would be the actual event processing logic
        // For demo purposes, we'll just print the event
        System.out.println("Processing transaction event: " + eventData);
    }

    public void kafkaConsumerFallback(String eventData, Throwable throwable) {
        // Fallback when all retries are exhausted
        System.err.println("Failed to process transaction event after all retries: " + eventData);
        // In a real implementation, we might send to a dead letter queue
    }

    // Bulkhead configuration to limit concurrent calls
    @Bulkhead(name = "externalServiceBulkhead", type = Bulkhead.Type.THREADPOOL)
    public String executeWithBulkhead(String serviceName) {
        // This would be the actual service call
        return "Service executed with bulkhead protection";
    }
}