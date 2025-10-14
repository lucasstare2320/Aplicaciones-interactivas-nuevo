package com.uade.keepstar.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.uade.keepstar.entity.dto.OrderRequest;
import com.uade.keepstar.entity.dto.OrderResponse;
import com.uade.keepstar.entity.dto.ProductRequest;
import com.uade.keepstar.entity.dto.ProductResponse;
import com.uade.keepstar.exceptions.CategoryNotFoundException;
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.service.OrderService;
import com.uade.keepstar.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping
    public List<OrderResponse> getOrder(){
        return orderService.getOrder();
    }
    @GetMapping("/{id}")
    public ProductResponse getIDList(@PathVariable Long id){
        return orderService.getIDList(id);
    }

    @PostMapping
    public OrderResponse crearOrder(@RequestBody OrderRequest request) throws CategoryNotFoundException, UserNotFoundException {
        return orderService.crearOrder(request);
    }
}
