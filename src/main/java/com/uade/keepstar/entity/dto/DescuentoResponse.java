package com.uade.keepstar.entity.dto;

import com.uade.keepstar.entity.Discount;

import lombok.Data;

@Data
public class DescuentoResponse {
    private String code;
    private Long id;
    private int procentaje;
    private Long producto;

    public  DescuentoResponse(Discount descuento){
            this.code = descuento.getCode();
            this.id = descuento.getId();
            this.procentaje = descuento.getPorcentaje();
            this.producto = descuento.getProduct().getId();

        }
}
