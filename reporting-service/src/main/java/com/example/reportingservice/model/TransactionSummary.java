package com.example.reportingservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction_summary")
public class TransactionSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_date")
    private String transactionDate; // YYYY-MM-DD format

    @Column(name = "transaction_type")
    private String transactionType; // DEBIT, CREDIT

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "transaction_count")
    private Long transactionCount;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Long transactionCount) {
        this.transactionCount = transactionCount;
    }

    // Constructors
    public TransactionSummary() {}

    public TransactionSummary(Long id, String transactionDate, String transactionType, Double totalAmount, Long transactionCount) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.totalAmount = totalAmount;
        this.transactionCount = transactionCount;
    }
}