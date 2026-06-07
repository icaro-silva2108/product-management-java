package com.icaro.product_management.category.controller;

import com.icaro.product_management.category.dtos.CategoryRequestDTO;
import com.icaro.product_management.category.dtos.CategoryResponseDTO;
import com.icaro.product_management.category.service.CategoryService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@ActiveProfiles("test")
public class CategoryControllerTest {

    @MockitoBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private CategoryRequestDTO requestDTO;
    private CategoryResponseDTO responseDTO;
    @BeforeEach
    void setup() {

        requestDTO = new CategoryRequestDTO("category_1");
        responseDTO = new CategoryResponseDTO(1L, "category_1", Instant.now());
    }

    @Test
    @DisplayName("should create category successfully returning 201")
    void createCategoryEndpoint_success() throws Exception {

        String requestBody = objectMapper.writeValueAsString(requestDTO);

        when(categoryService.createCategory(requestDTO))
                .thenReturn(responseDTO);

        mockMvc.perform(
                post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.name").value(requestDTO.name()));
    }

    @Test
    @DisplayName("should return 400 bad_request caused by invalid JSON")
    void createCategory_invalidJson() throws Exception {

        mockMvc.perform(
                post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("any invalid JSON body")
        )
                .andExpect(status().isBadRequest())

                .andExpect(jsonPath("$.message").value("invalid JSON body request"));
    }

    @Test
    @DisplayName("should return 200 listing categories")
    void listCategories_success() throws Exception {

        when(categoryService.listAllCategories())
                .thenReturn(List.of(responseDTO));

        mockMvc.perform(
                get("/categories")
        )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].name").value(responseDTO.name()));
    }

    @Test
    @DisplayName("should return 204 no_content deleting a category")
    void deleteCategory_success() throws Exception {

        doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(
                delete("/categories/1")
        )
                .andExpect(status().isNoContent());
    }
}