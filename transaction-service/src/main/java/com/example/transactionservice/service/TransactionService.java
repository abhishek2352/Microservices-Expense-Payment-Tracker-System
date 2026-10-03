package com.example.transactionservice.service;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.TransactionRepository;
import com.example.transactionservice.event.TransactionEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    private static final String TRANSACTION_TOPIC = "transaction-events";

    public Transaction initiateTransaction(Transaction transaction) {
        // Set initial status as PENDING
        transaction.setStatus("PENDING");
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Publish TransactionInitiated event
        TransactionEvent event = new TransactionEvent(
            UUID.randomUUID().toString(),
            "TransactionInitiated",
            savedTransaction.getId(),
            savedTransaction.getUserId(),
            savedTransaction.getAmount(),
            savedTransaction.getType(),
            savedTransaction.getDescription()
        );

        kafkaTemplate.send(TRANSACTION_TOPIC, event);

        return savedTransaction;
    }

    public void confirmTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
        transaction.setStatus("CONFIRMED");
        transactionRepository.save(transaction);

        // Publish TransactionConfirmed event
        TransactionEvent event = new TransactionEvent(
            UUID.randomUUID().toString(),
            "TransactionConfirmed",
            transaction.getId(),
            transaction.getUserId(),
            transaction.getAmount(),
            transaction.getType(),
            transaction.getDescription()
        );

        kafkaTemplate.send(TRANSACTION_TOPIC, event);
    }

    public void failTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
        transaction.setStatus("FAILED");
        transactionRepository.save(transaction);

        // Publish TransactionFailed event
        TransactionEvent event = new TransactionEvent(
            UUID.randomUUID().toString(),
            "TransactionFailed",
            transaction.getId(),
            transaction.getUserId(),
            transaction.getAmount(),
            transaction.getType(),
            transaction.getDescription()
        );

        kafkaTemplate.send(TRANSACTION_TOPIC, event);
    }
}