package com.uade.keepstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.uade.keepstar.entity.dto.ProductRequest;
import com.uade.keepstar.entity.dto.ProductResponse;
import com.uade.keepstar.exceptions.CategoryNotFoundException;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductResponse> getProducts(
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(required = false) Long categoryId
    ) {
        return productService.getProducts(minPrice, maxPrice, categoryId);
    }

    @GetMapping("/{id}")
    public ProductResponse getIDList(@PathVariable Long id) throws ProductNotFoundException {
        return productService.getIDList(id);
    }

    @PostMapping
    public ProductResponse crearProduct(@RequestBody ProductRequest request)
            throws CategoryNotFoundException, UserNotFoundException {
        return productService.crearProducto(request);
    }

    @PutMapping("/{id}")
    public ProductResponse actualizarProduct(@PathVariable Long id,
            @RequestBody ProductRequest request)
            throws CategoryNotFoundException, UserNotFoundException, ProductNotFoundException {
        return productService.actualizarProducto(id, request);
    }
}
