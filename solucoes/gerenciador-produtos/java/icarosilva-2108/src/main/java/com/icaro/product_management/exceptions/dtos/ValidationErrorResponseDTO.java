package com.icaro.product_management.exceptions.dtos;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorResponseDTO(

        Instant timestamp,
        int status,
        String message,
        Map<String, String> errors
) {
    public ValidationErrorResponseDTO(int status, String message, Map<String, String> errors) {

        this(Instant.now(), status, message, errors);
    }
}