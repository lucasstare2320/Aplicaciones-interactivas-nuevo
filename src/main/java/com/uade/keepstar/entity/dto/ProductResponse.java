package com.uade.keepstar.entity.dto;

import com.uade.keepstar.entity.Product;
import lombok.*;

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
    private int discount;      // nuevo

    private Long categoryId;     // nuevo
    private String categoryName; // nuevo


    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.active = product.isActive();
        this.discount = product.getDiscount(); 

        if (product.getCategory() != null) {
            this.categoryId = product.getCategory().getId();
            this.categoryName = product.getCategory().getName();
        }

    }

    public static ProductResponse of(Product p) {
        return new ProductResponse(p);
    }
}
