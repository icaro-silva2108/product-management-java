package com.icaro.product_management.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icaro.product_management.category.dtos.CategoryResponseForProductDTO;
import com.icaro.product_management.product.dtos.ProductRequestDTO;
import com.icaro.product_management.product.dtos.ProductResponseDTO;
import com.icaro.product_management.product.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@ActiveProfiles("test")
public class ProductControllerTest {

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private ProductRequestDTO requestDTO;
    private ProductResponseDTO responseDTO;
    private CategoryResponseForProductDTO categoryForProductDTO;
    @BeforeEach
    void setup() {

        requestDTO = new ProductRequestDTO(
                "product_1",
                "description",
                BigDecimal.valueOf(99.90),
                10,
                1L
        );
        categoryForProductDTO = new CategoryResponseForProductDTO(
                1L,
                "category_1"
        );
        responseDTO = new ProductResponseDTO(
                1L,
                requestDTO.name(),
                requestDTO.description(),
                requestDTO.price(),
                requestDTO.stock(),
                categoryForProductDTO,
                Instant.now(),
                Instant.now()
        );
    }

    @Test
    @DisplayName("should create product successfully returning 201")
    void createProductEndpoint_success() throws Exception {

        when(productService.createProduct(requestDTO))
                .thenReturn(responseDTO);

        String requestBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").value(requestDTO.name()))

                .andExpect(jsonPath("$.category").exists())
                .andExpect(jsonPath("$.category.name").value(categoryForProductDTO.name()));
    }

    @Test
    @DisplayName("should return 400 bad_request caused by invalid JSON")
    void createProductEndpoint_invalidJson() throws Exception {

        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("any invalid JSON body")
        )
                .andExpect(status().isBadRequest())

                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("invalid JSON body request"));
    }

    @Test
    @DisplayName("should return 400 bad_request caused by negative price and stock")
    void createProductEndpoint_beanValidationException() throws Exception {

        ProductRequestDTO wrongRequest = new ProductRequestDTO(
                "product_2",
                "new description",
                BigDecimal.valueOf(-99.90),
                -10,
                1L
        );

        String wrongRequestBody = objectMapper.writeValueAsString(wrongRequest);

        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongRequestBody)
        )
                .andExpect(status().isBadRequest())

                .andExpect(jsonPath("$.errors.price").value("product price must not be negative"))
                .andExpect(jsonPath("$.errors.stock").value("product stock must not be negative"));
    }

    @Test
    @DisplayName("should return the product with the given id")
    void findByIdEndpoint_success() throws Exception {

        when(productService.findProductById(1L))
                .thenReturn(responseDTO);

        mockMvc.perform(
                get("/products/1")
        )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("should return 200 listing products")
    void listProductsEndpoint_success() throws Exception {

        when(productService.listAllProducts(null, null, null))
                .thenReturn(List.of(responseDTO));

        mockMvc.perform(
                get("/products")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(responseDTO.name()));
    }

    @Test
    @DisplayName("should return 204 deleting a product")
    void deleteProductEndpoint_success() throws Exception {

        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(
                delete("/products/1")
        )
                .andExpect(status().isNoContent());
    }
}