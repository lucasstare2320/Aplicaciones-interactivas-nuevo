package com.uade.keepstar.service;

import java.util.List;

import com.uade.keepstar.entity.dto.ProductRequest;
import com.uade.keepstar.entity.dto.ProductResponse;
import com.uade.keepstar.exceptions.CategoryNotFoundException;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.exceptions.UserNotFoundException;


public interface ProductService {

    public List<ProductResponse> getProducts();

    public ProductResponse getIDList(Long id);

    public ProductResponse crearProducto(ProductRequest request) throws CategoryNotFoundException, UserNotFoundException;

    public ProductResponse actualizarProducto(Long id, ProductRequest request) throws CategoryNotFoundException, UserNotFoundException, ProductNotFoundException;
}