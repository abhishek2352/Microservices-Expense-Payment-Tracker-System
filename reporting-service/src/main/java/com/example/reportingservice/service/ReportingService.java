package com.example.reportingservice.service;

import com.example.reportingservice.model.TransactionSummary;
import com.example.reportingservice.repository.TransactionSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingService {

    @Autowired
    private TransactionSummaryRepository transactionSummaryRepository;

    public TransactionSummary updateTransactionSummary(TransactionSummary summary) {
        // In a real implementation, we would aggregate with existing data
        // For now, we'll just save it
        return transactionSummaryRepository.save(summary);
    }
}