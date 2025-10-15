package com.uade.keepstar.service;

import com.uade.keepstar.entity.dto.*;
import com.uade.keepstar.exceptions.*;
import java.util.List;

public interface ProductService {
    List<ProductResponse> getProducts(Double minPrice, Double maxPrice, Long categoryId) throws ProductNotFoundException;
    ProductResponse getIDList(Long id) throws ProductNotFoundException;
    ProductResponse crearProducto(ProductRequest request) throws CategoryNotFoundException, UserNotFoundException, DuplicateProductException;
    ProductResponse actualizarProducto(Long id, ProductRequest request)
            throws CategoryNotFoundException, UserNotFoundException, ProductNotFoundException;
    void deleteProduct(Long id) throws ProductNotFoundException;
}
