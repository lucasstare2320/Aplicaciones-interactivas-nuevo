package com.uade.keepstar.service;

import java.util.List;

import com.uade.keepstar.entity.dto.ProductRequest;
import com.uade.keepstar.entity.dto.ProductResponse;
import com.uade.keepstar.exceptions.CategoryNotFoundException;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.exceptions.UserNotFoundException;

public interface ProductService {

    List<ProductResponse> getProducts(Double minPrice, Double maxPrice, Long categoryId) throws ProductNotFoundException;

    ProductResponse getIDList(Long id) throws ProductNotFoundException;

    ProductResponse crearProducto(ProductRequest request)
            throws CategoryNotFoundException, UserNotFoundException;

    ProductResponse actualizarProducto(Long id, ProductRequest request)
            throws CategoryNotFoundException, UserNotFoundException, ProductNotFoundException;
}
