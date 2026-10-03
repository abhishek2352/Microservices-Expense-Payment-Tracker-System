package com.example.transactionservice.controller;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> initiateTransaction(@RequestBody Transaction transaction) {
        Transaction initiatedTransaction = transactionService.initiateTransaction(transaction);
        return ResponseEntity.ok(initiatedTransaction);
    }

    @PutMapping("/{transactionId}/confirm")
    public ResponseEntity<Void> confirmTransaction(@PathVariable Long transactionId) {
        transactionService.confirmTransaction(transactionId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{transactionId}/fail")
    public ResponseEntity<Void> failTransaction(@PathVariable Long transactionId) {
        transactionService.failTransaction(transactionId);
        return ResponseEntity.ok().build();
    }
}