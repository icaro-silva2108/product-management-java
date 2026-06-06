package com.icaro.product_management.product.dtos;

import com.icaro.product_management.category.dtos.CategoryResponseForProductDTO;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductResponseDTO(

        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        CategoryResponseForProductDTO category,
        Instant createdAt,
        Instant updatedAt
) {}