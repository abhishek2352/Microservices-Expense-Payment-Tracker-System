package com.example.notificationservice.consumer;

import com.example.notificationservice.event.TransactionEvent;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.service.NotificationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventConsumer {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "transaction-events", groupId = "notification-service-group")
    public void consumeTransactionEvent(ConsumerRecord<String, TransactionEvent> record) {
        TransactionEvent event = record.value();
        System.out.println("Received transaction event: " + event.getEventType());

        // Process different event types
        switch (event.getEventType()) {
            case "TransactionConfirmed":
                // Send notification for confirmed transaction
                Notification confirmedNotification = new Notification();
                confirmedNotification.setTransactionId(event.getTransactionId());
                confirmedNotification.setUserId(event.getUserId());
                confirmedNotification.setType("EMAIL");
                confirmedNotification.setStatus("PENDING");
                confirmedNotification.setRecipient("user@example.com"); // In real app, get from user service
                confirmedNotification.setSubject("Transaction Confirmation");
                confirmedNotification.setContent("Your transaction of $" + event.getAmount() + " has been confirmed.");

                notificationService.sendNotification(confirmedNotification);
                break;
            case "TransactionFailed":
                // Send failure notification
                Notification failedNotification = new Notification();
                failedNotification.setTransactionId(event.getTransactionId());
                failedNotification.setUserId(event.getUserId());
                failedNotification.setType("EMAIL");
                failedNotification.setStatus("PENDING");
                failedNotification.setRecipient("user@example.com"); // In real app, get from user service
                failedNotification.setSubject("Transaction Failed");
                failedNotification.setContent("Your transaction of $" + event.getAmount() + " has failed.");

                notificationService.sendNotification(failedNotification);
                break;
            default:
                System.out.println("Ignoring event type: " + event.getEventType());
        }
    }
}