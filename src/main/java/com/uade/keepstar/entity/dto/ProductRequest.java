package com.uade.keepstar.entity.dto;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private String description;
    private float price;
    private int stock;
    private Long categoryId;
    private Long sellerId;
}

