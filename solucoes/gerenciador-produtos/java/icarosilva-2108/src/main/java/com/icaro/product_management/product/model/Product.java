package com.icaro.product_management.product.model;

import com.icaro.product_management.category.model.Category;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Setter
    @Column(name = "description")
    private String description;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;


    public Product(
            String name,
            String description,
            BigDecimal price,
            Integer stock,
            Category category
    ) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.description = description;
        this.price = Objects.requireNonNull(price, "price cannot be null");
        this.stock = Objects.requireNonNull(stock, "stock cannot be null");
        this.category = Objects.requireNonNull(category, "category cannot be null");

        if (name.isBlank()) {
            throw new IllegalArgumentException("name cannot be blank");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("stock cannot be negative");
        }
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("name cannot be blank");
        }
    }

    public void setPrice(BigDecimal price) {
        this.price = Objects.requireNonNull(price, "price cannot be null");
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
    }

    public void setStock(Integer stock) {
        this.stock = Objects.requireNonNull(stock, "stock cannot be null");
        if (stock < 0) {
            throw new IllegalArgumentException("stock cannot be negative");
        }
    }

    public void setCategory(Category category) {
        this.category = Objects.requireNonNull(category, "category cannot be null");
    }
}