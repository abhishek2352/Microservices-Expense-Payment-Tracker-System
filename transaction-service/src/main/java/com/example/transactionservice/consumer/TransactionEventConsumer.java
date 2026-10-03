package com.example.transactionservice.consumer;

import com.example.transactionservice.event.TransactionEvent;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.TransactionRepository;
import com.example.transactionservice.service.TransactionService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventConsumer {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "transaction-events", groupId = "transaction-service-group")
    public void consumeTransactionEvent(ConsumerRecord<String, TransactionEvent> record) {
        TransactionEvent event = record.value();
        System.out.println("Received transaction event: " + event.getEventType());

        // Process different event types
        switch (event.getEventType()) {
            case "TransactionInitiated":
                // In a real implementation, we would check funds here
                // For simplicity, we'll auto-confirm all transactions
                transactionService.confirmTransaction(event.getTransactionId());
                break;
            case "TransactionConfirmed":
                // Already confirmed, nothing to do
                break;
            case "TransactionFailed":
                // Already failed, nothing to do
                break;
            default:
                System.out.println("Unknown event type: " + event.getEventType());
        }
    }
}