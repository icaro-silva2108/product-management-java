package com.icaro.product_management.category.service;

import com.icaro.product_management.category.dtos.CategoryRequestDTO;
import com.icaro.product_management.category.dtos.CategoryResponseDTO;
import com.icaro.product_management.category.model.Category;
import com.icaro.product_management.category.repository.CategoryRepository;
import com.icaro.product_management.exceptions.CategoryAlreadyExistsException;
import com.icaro.product_management.exceptions.CategoryHasProductsException;
import com.icaro.product_management.exceptions.ResourceNotFoundException;
import com.icaro.product_management.product.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryRequestDTO requestDTO;
    @BeforeEach
    void setup() {

        category = new Category("category_1");
        ReflectionTestUtils.setField(category, "id", 1L);

        requestDTO = new CategoryRequestDTO("category_1");
    }

    @Test
    @DisplayName("should create category successfully")
    void createCategory_success() {

        when(categoryRepository.existsByName(requestDTO.name()))
                .thenReturn(false);

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(category);

        CategoryResponseDTO result = categoryService.createCategory(requestDTO);

        verify(categoryRepository).save(any(Category.class));

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(category.getId());
        assertThat(result.name()).isEqualTo(category.getName());
    }

    @Test
    @DisplayName("should throw CategoryAlreadyExistsException caused by existing category with the given name")
    void createCategory_duplicatedName() {

        when(categoryRepository.existsByName(requestDTO.name()))
                .thenReturn(true);

        assertThatThrownBy(
                () -> categoryService.createCategory(requestDTO)
        )
                .isInstanceOf(CategoryAlreadyExistsException.class)
                .hasMessageContaining(
                        String.format("category with name %s already exists", requestDTO.name())
                );
    }

    @Test
    @DisplayName("should throw ResourceNotFoundException caused by category not found")
    void deleteCategory_categoryNotFound() {

        when(categoryRepository.findById(category.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> categoryService.deleteCategory(category.getId())
        )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(
                        String.format("category with id %d not found", category.getId())
                );
    }

    @Test
    @DisplayName("should throw CategoryHasProductException caused by deleting category with associated products")
    void deleteCategory_categoryWithProducts() {

        when(categoryRepository.findById(category.getId()))
                .thenReturn(Optional.of(category));

        when(productRepository.existsByCategoryId(category.getId()))
                .thenReturn(true);

        assertThatThrownBy(
                () -> categoryService.deleteCategory(category.getId())
        )
                .isInstanceOf(CategoryHasProductsException.class)
                .hasMessageContaining("could not delete category with associated products");
    }
}