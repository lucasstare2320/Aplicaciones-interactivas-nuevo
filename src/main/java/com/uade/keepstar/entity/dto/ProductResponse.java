package com.uade.keepstar.entity.dto;

import java.util.List;

import com.uade.keepstar.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private float price;
    private int stock;
    private boolean active;
    private Long categoryId;
    private String categoryName;
    private Integer discount;
    private String image;

    // >>> AHORA trae TODAS las imágenes del producto en base64
    private List<String> images;

    // Conservamos el constructor que usa Product (lo pedía tu implementación)
    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.active = product.isActive();
        this.categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
        this.categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
        this.discount = product.getDiscount() == null ? 0 : product.getDiscount();
        this.image = product.getImage();
    }
}

