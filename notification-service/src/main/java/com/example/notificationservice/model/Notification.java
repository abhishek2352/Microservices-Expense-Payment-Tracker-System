package com.example.notificationservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long transactionId;
    private Long userId;
    private String type; // EMAIL, SMS, PUSH
    private String status; // PENDING, SENT, FAILED
    private String recipient;
    private String subject;
    private String content;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Constructors
    public Notification() {}

    public Notification(Long id, Long transactionId, Long userId, String type, String status, String recipient, String subject, String content) {
        this.id = id;
        this.transactionId = transactionId;
        this.userId = userId;
        this.type = type;
        this.status = status;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }
}