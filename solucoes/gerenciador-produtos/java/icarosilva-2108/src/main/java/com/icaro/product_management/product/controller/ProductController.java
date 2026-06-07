package com.icaro.product_management.product.controller;

import com.icaro.product_management.product.dtos.ProductRequestDTO;
import com.icaro.product_management.product.dtos.ProductResponseDTO;
import com.icaro.product_management.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO requestDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
            ) {

        return ResponseEntity
                .ok(productService.listAllProducts(categoryId, minPrice, maxPrice));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable("product-id") Long id) {

        return ResponseEntity
                .ok(productService.findProductById(id));
    }

    @PutMapping("/{product-id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable("product-id") Long id,
            @RequestBody @Valid ProductRequestDTO requestDTO
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.updateProduct(id, requestDTO));
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("product-id") Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}