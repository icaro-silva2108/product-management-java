package com.icaro.product_management.product.service;

import com.icaro.product_management.category.dtos.CategoryResponseForProductDTO;
import com.icaro.product_management.category.model.Category;
import com.icaro.product_management.category.repository.CategoryRepository;
import com.icaro.product_management.exceptions.ProductAlreadyExistsException;
import com.icaro.product_management.exceptions.ResourceNotFoundException;
import com.icaro.product_management.product.dtos.ProductRequestDTO;
import com.icaro.product_management.product.dtos.ProductResponseDTO;
import com.icaro.product_management.product.model.Product;
import com.icaro.product_management.product.repository.ProductRepository;
import com.icaro.product_management.product.specs.ProductSpecification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private ProductResponseDTO toResponseDto(Product product) {

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                new CategoryResponseForProductDTO(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                ),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        Category category = categoryRepository.findById(requestDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("category with id %d not found", requestDTO.categoryId()))
                );

        if (productRepository.existsByName(requestDTO.name())) {
            throw new ProductAlreadyExistsException(
                    String.format("product with name %s already exists", requestDTO.name())
            );
        }

        Product product = new Product(
                requestDTO.name(),
                requestDTO.description(),
                requestDTO.price(),
                requestDTO.stock(),
                category
        );

        Product savedProduct = productRepository.save(product);
        return toResponseDto(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> listAllProducts(
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice
    ) {

        Specification<Product> spec = Specification.unrestricted();

        if (categoryId != null) {

            spec = spec.and(ProductSpecification.hasCategory(categoryId));
        }
        if (minPrice != null) {

            spec = spec.and(ProductSpecification.hasMinPrice(minPrice));
        }
        if (maxPrice != null) {

            spec = spec.and(ProductSpecification.hasMaxPrice(maxPrice));
        }

        return productRepository.findAll(spec).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("product with id %d not found", id)
                ));

        return toResponseDto(product);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("product with id %d not found", id)
                ));

        Product existing = productRepository.findByName(requestDTO.name())
                .orElse(null);

        if (existing != null && !product.getId().equals(existing.getId())) {

            throw new ProductAlreadyExistsException(
                    String.format("product with name %s already exists", requestDTO.name())
            );
        }

        Category category = categoryRepository.findById(requestDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("category with id %d not found", requestDTO.categoryId())
                ));

        product.setName(requestDTO.name());
        product.setDescription(requestDTO.description());
        product.setPrice(requestDTO.price());
        product.setStock(requestDTO.stock());
        product.setCategory(category);

        Product productSaved = productRepository.saveAndFlush(product);
        return toResponseDto(productSaved);
    }

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("product with id %d not found", id)
                ));

        productRepository.delete(product);
    }
}