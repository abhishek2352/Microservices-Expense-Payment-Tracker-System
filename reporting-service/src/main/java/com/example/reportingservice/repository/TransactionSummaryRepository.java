package com.example.reportingservice.repository;

import com.example.reportingservice.model.TransactionSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionSummaryRepository extends JpaRepository<TransactionSummary, Long> {
}