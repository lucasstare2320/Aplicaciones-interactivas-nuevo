package com.uade.keepstar.service;


import com.uade.keepstar.entity.dto.ApplyDiscountRequest;
import com.uade.keepstar.entity.dto.ApplyDiscountResponse;
import com.uade.keepstar.entity.dto.DescuentoRequest;
import com.uade.keepstar.entity.dto.DescuentoResponse;
import com.uade.keepstar.exceptions.ProductNotFoundException;


public interface DescuentoService {

     DescuentoResponse create(DescuentoRequest request) throws ProductNotFoundException;

     DescuentoResponse getByCode (String code);

     ApplyDiscountResponse apply(ApplyDiscountRequest request);
}