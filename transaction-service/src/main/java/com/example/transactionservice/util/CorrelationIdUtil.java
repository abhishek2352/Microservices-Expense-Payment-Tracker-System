package com.example.transactionservice.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CorrelationIdUtil {
    public static String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    public static String getCorrelationIdHeader() {
        return "X-Correlation-ID";
    }
}