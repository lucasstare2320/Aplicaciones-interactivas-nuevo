package com.uade.keepstar.entity.dto;

import lombok.Data;

@Data
public class ProductRequest {
    
    private String name;
    private String description;
    private float price;
    private int stock;
    private String category;
    private Long user_id;


}
