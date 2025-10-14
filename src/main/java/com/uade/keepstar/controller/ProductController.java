package com.uade.keepstar.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.keepstar.entity.dto.ProductRequest;
import com.uade.keepstar.entity.dto.ProductResponse;
import com.uade.keepstar.exceptions.CategoryNotFoundException;
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("products")
    public List<ProductResponse> getProducts(){
        return productService.getProducts();
    }
    @GetMapping("products/{id}")
    public ProductResponse getIDList(@RequestBody Long id){
        return productService.getIDList(id);
    }

    @PostMapping("products")
    public ProductResponse crearProduct(@RequestBody ProductRequest request) throws CategoryNotFoundException, UserNotFoundException {
        return productService.crearProducto(request);
    }
    
}