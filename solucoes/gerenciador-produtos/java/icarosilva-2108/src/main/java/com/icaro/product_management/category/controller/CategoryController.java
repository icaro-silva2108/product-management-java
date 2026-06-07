package com.icaro.product_management.category.controller;

import com.icaro.product_management.category.dtos.CategoryRequestDTO;
import com.icaro.product_management.category.dtos.CategoryResponseDTO;
import com.icaro.product_management.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryRequestDTO requestDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> listCategories() {

        return ResponseEntity
                .ok(categoryService.listAllCategories());
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("category-id") Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}