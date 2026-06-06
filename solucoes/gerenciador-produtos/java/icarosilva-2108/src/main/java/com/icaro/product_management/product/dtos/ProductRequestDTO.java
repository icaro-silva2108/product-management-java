package com.icaro.product_management.product.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "product name is required")
        String name,

        String description,

        @NotNull(message = "product price is required")
        @PositiveOrZero(message = "product price must not be negative")
        BigDecimal price,

        @NotNull
        @Min(value = 0, message = "product stock must not be negative")
        Integer stock,

        @NotNull(message = "product category is required")
        Long categoryId
) {}