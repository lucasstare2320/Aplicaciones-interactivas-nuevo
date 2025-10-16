package com.uade.keepstar.entity.dto;

import com.uade.keepstar.entity.Discount;
import lombok.Data;

@Data
public class DescuentoResponse {
    private Long id;
    private String code;
    private int porcentaje;   
    private Long producto;    

    public DescuentoResponse(Discount descuento) {
        this.id = descuento.getId();
        this.code = descuento.getCode();
        this.porcentaje = descuento.getPorcentaje();
        this.producto = descuento.getProduct() != null ? descuento.getProduct().getId() : null;
    }
}
