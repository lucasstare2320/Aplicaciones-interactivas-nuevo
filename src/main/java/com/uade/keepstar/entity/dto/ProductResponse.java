package com.uade.keepstar.entity.dto;



import com.uade.keepstar.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse{
    
    private Long id;
    private String name;
    private String description;
    private float price;
    private int stock;
    private boolean active;

    public ProductResponse (Product product){
    id= product.getId();
    name = product.getName();
    description = product.getDescription();
    price = product.getPrice();
    stock = product.getStock();
    active = product.isActive();

    }
} 
