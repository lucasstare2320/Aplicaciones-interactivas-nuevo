package com.uade.keepstar.entity.dto;

import lombok.Data;

@Data
public class DescuentoRequest {
    private String code;
    private int porcentaje;
    private Long producto;
}
