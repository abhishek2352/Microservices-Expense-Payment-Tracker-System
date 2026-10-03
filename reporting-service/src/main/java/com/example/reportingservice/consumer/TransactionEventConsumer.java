package com.example.reportingservice.consumer;

import com.example.reportingservice.event.TransactionEvent;
import com.example.reportingservice.model.TransactionSummary;
import com.example.reportingservice.repository.TransactionSummaryRepository;
import com.example.reportingservice.service.ReportingService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TransactionEventConsumer {

    @Autowired
    private TransactionSummaryRepository transactionSummaryRepository;

    @Autowired
    private ReportingService reportingService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @KafkaListener(topics = "transaction-events", groupId = "reporting-service-group")
    public void consumeTransactionEvent(ConsumerRecord<String, TransactionEvent> record) {
        TransactionEvent event = record.value();
        System.out.println("Received reporting event: " + event.getEventType());

        // Only process confirmed transactions for reporting
        if ("TransactionConfirmed".equals(event.getEventType())) {
            // Update the summary for today's date
            String today = DATE_FORMAT.format(new Date());

            // In a real implementation, we would use a more sophisticated upsert
            // For simplicity, we'll just create a new entry each time
            TransactionSummary summary = new TransactionSummary();
            summary.setTransactionDate(today);
            summary.setTransactionType(event.getType());
            summary.setTotalAmount(event.getAmount());
            summary.setTransactionCount(1L);

            reportingService.updateTransactionSummary(summary);
        }
    }
}