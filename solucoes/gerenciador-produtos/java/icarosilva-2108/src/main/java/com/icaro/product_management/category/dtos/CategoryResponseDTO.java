package com.icaro.product_management.category.dtos;

import java.time.Instant;

public record CategoryResponseDTO(

        Long id,
        String name,
        Instant createdAt
) {}