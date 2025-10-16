package com.uade.keepstar.entity.dto;

import lombok.Data;

@Data

public class ApplyDiscountRequest {
    private String code;
    private Double orderTotal; 
    
}
