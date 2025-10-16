package com.uade.keepstar.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplyDiscountResponse {
    private String code;
    private Double orderTotal;
    private Double discountAmount;
    private Double totalToPay;
    private String description;
    
}
