package com.icaro.product_management.category.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(

        @NotBlank(message = "category name is required")
        String name
) {}