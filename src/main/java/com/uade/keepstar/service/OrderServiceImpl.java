package com.uade.keepstar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.keepstar.entity.Order;
import com.uade.keepstar.entity.OrderItem;
import com.uade.keepstar.entity.Product;
import com.uade.keepstar.entity.User;
import com.uade.keepstar.entity.dto.OrderRequest;
import com.uade.keepstar.entity.dto.OrderResponse;
import com.uade.keepstar.entity.dto.ProductRequest;
import com.uade.keepstar.exceptions.CategoryNotFoundException;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.repository.OrderRepository;
import com.uade.keepstar.repository.ProductRepository;
import com.uade.keepstar.repository.UserRepository;


@Service

public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository producterRepository;
    @Autowired
    private UserRepository userRepository;
    

    @Override
    public List<OrderResponse> getOrder() {
        return null;
    }

    @Override
    public OrderResponse getIDList(Long id) {
        return null;

    }

    @Override
    public OrderResponse crearOrder(OrderRequest request) throws ProductNotFoundException, UserNotFoundException {
        Optional<User> user = userRepository.findById(request.getUserId());
        if (!user.isPresent()) {
            throw new UserNotFoundException();                    
        }
        List <Long > ids= request.getItems().stream().map(item -> item.getProductId()).toList();
        List <Product> products = producterRepository.findAllById(ids);
        List <OrderItem> items = products.stream().map(product -> new OrderItem(product, 1)).toList();
        return null;
        //return OrderRepository.save(Order.builder().user(user.get()).items(items).build());
            
    
    }
}


