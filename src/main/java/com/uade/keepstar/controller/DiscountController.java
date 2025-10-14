package com.uade.keepstar.controller;

import org.springframework.web.bind.annotation.RestController;

import com.uade.keepstar.entity.dto.DescuentoRequest;
import com.uade.keepstar.entity.dto.DescuentoResponse;
import com.uade.keepstar.exceptions.ProductNotFoundException;

import com.uade.keepstar.service.DescuentoService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("descuento")
public class DiscountController {
    @Autowired
    private DescuentoService descuentoService;

    @PostMapping
    public DescuentoResponse crear(@RequestBody DescuentoRequest request) throws ProductNotFoundException {
        return descuentoService.create(request);
    }
    @GetMapping("/{code}")
    public DescuentoResponse getCode(@PathVariable String code){
        return  descuentoService.getByCode(code);
        }


}
