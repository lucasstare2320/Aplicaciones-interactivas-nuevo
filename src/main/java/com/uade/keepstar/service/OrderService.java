package com.uade.keepstar.service;

import java.util.List;

import com.uade.keepstar.entity.dto.OrderRequest;
import com.uade.keepstar.entity.dto.OrderResponse;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.exceptions.ProductWithoutException;
import com.uade.keepstar.exceptions.UserNotFoundException;


public interface OrderService {

    public List<OrderResponse> getOrder();

    public OrderResponse getIDList(Long id);

    public OrderResponse crearOrder(OrderRequest request) throws ProductNotFoundException, UserNotFoundException, ProductWithoutException;

    
}