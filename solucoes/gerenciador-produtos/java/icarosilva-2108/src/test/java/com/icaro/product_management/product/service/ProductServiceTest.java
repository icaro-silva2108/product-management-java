package com.icaro.product_management.product.service;

import com.icaro.product_management.category.model.Category;
import com.icaro.product_management.category.repository.CategoryRepository;
import com.icaro.product_management.exceptions.ProductAlreadyExistsException;
import com.icaro.product_management.exceptions.ResourceNotFoundException;
import com.icaro.product_management.product.dtos.ProductRequestDTO;
import com.icaro.product_management.product.dtos.ProductResponseDTO;
import com.icaro.product_management.product.model.Product;
import com.icaro.product_management.product.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Category category;
    private ProductRequestDTO requestDTO;
    @BeforeEach
    void setup() {

        category = new Category("category_1");
        product = new Product("product_1", "description", BigDecimal.valueOf(99.90), 10, category);

        ReflectionTestUtils.setField(category, "id", 1L);
        ReflectionTestUtils.setField(product, "id", 1L);

        requestDTO = new ProductRequestDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                category.getId()
        );
    }

    @Test
    @DisplayName("should create product successfully")
    void createProduct_success() {

        when(categoryRepository.findById(requestDTO.categoryId()))
                .thenReturn(Optional.of(category));

        when(productRepository.existsByName(requestDTO.name()))
                .thenReturn(false);

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductResponseDTO result = productService.createProduct(requestDTO);

        verify(productRepository).save(any(Product.class));

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(product.getId());
        assertThat(result.name()).isEqualTo(product.getName());
        assertThat(result.category().id()).isEqualTo(category.getId());
    }

    @Test
    @DisplayName("should throw ResourceNotFoundException cause by inexistent category")
    void createProduct_categoryNotFound() {

        when(categoryRepository.findById(requestDTO.categoryId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> productService.createProduct(requestDTO)
        )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(
                        String.format("category with id %d not found", requestDTO.categoryId())
                );
    }

    @Test
    @DisplayName("should throw ProductAlreadyExistsException caused by existing product with the given name")
    void createProduct_duplicatedName() {

        when(categoryRepository.findById(category.getId()))
                .thenReturn(Optional.of(category));

        when(productRepository.existsByName(requestDTO.name()))
                .thenReturn(true);

        assertThatThrownBy(
                () -> productService.createProduct(requestDTO)
        )
                .isInstanceOf(ProductAlreadyExistsException.class)
                .hasMessageContaining(
                        String.format("product with name %s already exists", requestDTO.name())
                );
    }

    @Test
    @DisplayName("should update product successfully")
    void updateProduct_success() {

        Category newCategory = new Category("category_2");
        ReflectionTestUtils.setField(newCategory, "id", 2L);

        ProductRequestDTO updateRequestDTO = new ProductRequestDTO(
                "product_2",
                "new description",
                BigDecimal.valueOf(29.90),
                50,
                newCategory.getId()
        );

        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));

        when(productRepository.findByName(updateRequestDTO.name()))
                .thenReturn(Optional.empty());

        when(categoryRepository.findById(newCategory.getId()))
                .thenReturn(Optional.of(newCategory));

        when(productRepository.saveAndFlush(any(Product.class)))
                .thenReturn(product);

        ProductResponseDTO result = productService.updateProduct(product.getId(), updateRequestDTO);

        verify(productRepository).saveAndFlush(any(Product.class));

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(product.getId());
        assertThat(result.name()).isEqualTo("product_2");
        assertThat(result.price()).isEqualTo(BigDecimal.valueOf(29.90));
        assertThat(result.stock()).isEqualTo(50);
        assertThat(result.category().id()).isEqualTo(newCategory.getId());
    }

    @Test
    @DisplayName("should throw ProductAlreadyExistsException caused by existing product with the given name")
    void updateProduct_duplicatedName() {

        Product existingNameProduct = new Product(
                "product_2",
                "new description",
                BigDecimal.valueOf(29.90),
                50,
                category
        );
        ReflectionTestUtils.setField(existingNameProduct, "id", 2L);

        ProductRequestDTO updateRequestDTO = new ProductRequestDTO(
                "product_2",
                "new description",
                BigDecimal.valueOf(59.90),
                40,
                2L
        );

        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));

        when(productRepository.findByName(updateRequestDTO.name()))
                .thenReturn(Optional.of(existingNameProduct));

        assertThatThrownBy(
                () -> productService.updateProduct(product.getId(), updateRequestDTO)
        )
                .isInstanceOf(ProductAlreadyExistsException.class)
                .hasMessageContaining(
                        String.format("product with name %s already exists", updateRequestDTO.name())
                );
    }

    @Test
    @DisplayName("should throw ResourceNotFound caused by product not found")
    void deleteProduct_productNotFound() {

        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> productService.deleteProduct(product.getId())
        )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(
                        String.format("product with id %d not found", product.getId())
                );
    }

    @Test
    @DisplayName("should find product successfully")
    void findProductById_success() {

        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.findProductById(product.getId());

        assertThat(result.id()).isEqualTo(product.getId());
        assertThat(result.name()).isEqualTo(product.getName());
    }

    @Test
    @DisplayName("should throw ResourceNotFoundException caused by product not found")
    void findProductById_productNotFound() {

        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> productService.findProductById(product.getId())
        )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(
                        String.format("product with id %d not found", product.getId())
                );
    }
}