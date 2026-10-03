package com.example.transactionservice.event;

public class TransactionEvent {
    private String eventId;
    private String eventType; // TransactionInitiated, TransactionConfirmed, TransactionFailed
    private Long transactionId;
    private Long userId;
    private Double amount;
    private String type; // DEBIT, CREDIT
    private String description;

    // Getters and Setters
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Constructors
    public TransactionEvent() {}

    public TransactionEvent(String eventId, String eventType, Long transactionId, Long userId, Double amount, String type, String description) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }
}