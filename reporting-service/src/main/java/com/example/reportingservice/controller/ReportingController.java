package com.example.reportingservice.controller;

import com.example.reportingservice.model.TransactionSummary;
import com.example.reportingservice.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    @Autowired
    private ReportingService reportingService;

    // In a real implementation, we would have proper aggregation endpoints
    @GetMapping("/summary")
    public ResponseEntity<List<TransactionSummary>> getTransactionSummary() {
        // This is just a placeholder - in reality we'd query the repository
        return ResponseEntity.ok().build();
    }
}