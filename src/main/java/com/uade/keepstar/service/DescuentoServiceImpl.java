package com.uade.keepstar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.keepstar.entity.Discount;
import com.uade.keepstar.entity.Product;
import com.uade.keepstar.entity.dto.DescuentoRequest;
import com.uade.keepstar.entity.dto.DescuentoResponse;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.repository.DescuentoRepository;
import com.uade.keepstar.repository.ProductRepository;

@Service
public class DescuentoServiceImpl implements DescuentoService{
    @Autowired
    private DescuentoRepository descuentoRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public DescuentoResponse create(DescuentoRequest request) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(request.getProducto());
        if(!product.isPresent()){
            throw new ProductNotFoundException();
            }
        Discount descuento = descuentoRepository.save(Discount.builder().code(request.getCode()).porcentaje(request.getPorcentaje()).product(product.get()).build());
        return new DescuentoResponse(descuento); 
    }
    @Override
    public DescuentoResponse getByCode(String code) {
        Optional<Discount> descuento = descuentoRepository.findByCode(code);
        return new DescuentoResponse(descuento.get()) ;

    }
    
}
