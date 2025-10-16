package com.uade.keepstar.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.uade.keepstar.entity.dto.*;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.service.DescuentoService;

@RestController
@RequestMapping("discount")
public class DiscountController {

    @Autowired
    private DescuentoService descuentoService;

    @PostMapping
    public DescuentoResponse crear(@RequestBody DescuentoRequest request) throws ProductNotFoundException {
        return descuentoService.create(request);
    }

    @GetMapping("/{code}")
    public DescuentoResponse getCode(@PathVariable String code) {
        return descuentoService.getByCode(code);
    }

    @PostMapping("/apply")
    public ApplyDiscountResponse apply(@RequestBody ApplyDiscountRequest request) {
        return descuentoService.apply(request);
    }
}
