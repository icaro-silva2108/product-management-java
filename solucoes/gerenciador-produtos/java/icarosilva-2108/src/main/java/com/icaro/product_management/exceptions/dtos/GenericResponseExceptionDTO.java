package com.icaro.product_management.exceptions.dtos;

import java.time.Instant;

public record GenericResponseExceptionDTO(

        Instant timestamp,
        int status,
        String message
) {
    public GenericResponseExceptionDTO(int status, String message) {
        this(Instant.now(), status, message);
    }
}