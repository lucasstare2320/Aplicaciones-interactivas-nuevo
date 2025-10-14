package com.uade.keepstar.entity.dto;

import java.util.List;

import lombok.Data;

@Data



public class OrderRequest {
    private Long userId;
    
    private List<OrdenItemResquest> items;
    
    
}
