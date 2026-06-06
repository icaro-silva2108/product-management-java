package com.icaro.product_management.product.specs;

import com.icaro.product_management.product.model.Product;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class ProductSpecification {

    private ProductSpecification() {}

    public static Specification<Product> hasCategory(Long categoryId) {

        return (root, query, cb) ->
                cb.equal(
                        root.get("category").get("id"),
                        categoryId
                );
    }

    public static Specification<Product> hasMinPrice(BigDecimal minPrice){

        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(
                        root.get("price"),
                        minPrice
                );
    }

    public static Specification<Product> hasMaxPrice(BigDecimal maxPrice) {

        return (root, query, cb) ->
                cb.lessThanOrEqualTo(
                        root.get("price"),
                        maxPrice
                );
    }
}