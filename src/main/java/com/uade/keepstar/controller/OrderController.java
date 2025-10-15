package com.uade.keepstar.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.keepstar.entity.dto.OrderRequest;
import com.uade.keepstar.entity.dto.OrderResponse;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.exceptions.ProductWithoutException;
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.service.OrderService;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderResponse> getOrder() {
        return orderService.getOrder();
    }

    @GetMapping("/{id}")
    public OrderResponse getIDList(@PathVariable Long id) {
        return orderService.getIDList(id);
    }

    @PostMapping
    public OrderResponse crearOrder(@RequestBody OrderRequest request)
            throws ProductNotFoundException, UserNotFoundException, ProductWithoutException {
        return orderService.crearOrder(request);
    }
}
