package com.icaro.product_management.category.service;

import com.icaro.product_management.category.dtos.CategoryRequestDTO;
import com.icaro.product_management.category.dtos.CategoryResponseDTO;
import com.icaro.product_management.category.model.Category;
import com.icaro.product_management.category.repository.CategoryRepository;

import com.icaro.product_management.exceptions.CategoryAlreadyExistsException;
import com.icaro.product_management.exceptions.CategoryHasProductsException;
import com.icaro.product_management.exceptions.ResourceNotFoundException;
import com.icaro.product_management.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private CategoryResponseDTO toResponseDto(Category category) {

        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getCreatedAt()
        );
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {

        if (categoryRepository.existsByName(requestDTO.name())) {
            throw new CategoryAlreadyExistsException(
                    String.format("category with name %s already exists", requestDTO.name())
            );
        }

        Category category = new Category(requestDTO.name());

        Category savedCategory = categoryRepository.save(category);
        return toResponseDto(savedCategory);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> listAllCategories() {

        return categoryRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("category with id %d not found", id))
                );

        if (productRepository.existsByCategoryId(id)) {
            throw new CategoryHasProductsException("could not delete category with associated products");
        }

        categoryRepository.delete(category);
    }
}