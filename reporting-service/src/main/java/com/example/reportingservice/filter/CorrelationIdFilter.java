package com.example.reportingservice.filter;

import com.example.reportingservice.util.CorrelationIdUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class CorrelationIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Get correlation ID from header or generate new one
        String correlationId = httpRequest.getHeader(CorrelationIdUtil.getCorrelationIdHeader());
        if (!StringUtils.hasText(correlationId)) {
            correlationId = CorrelationIdUtil.generateCorrelationId();
        }

        // Add to MDC for logging
        org.slf4j.MDC.put("correlationId", correlationId);

        // Add to response header so downstream services can see it
        ((HttpServletResponse) response).setHeader(CorrelationIdUtil.getCorrelationIdHeader(), correlationId);

        try {
            chain.doFilter(request, response);
        } finally {
            // Clean up MDC
            org.slf4j.MDC.remove("correlationId");
        }
    }
}