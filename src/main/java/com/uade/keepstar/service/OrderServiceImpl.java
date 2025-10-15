package com.uade.keepstar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.keepstar.entity.Order;
import com.uade.keepstar.entity.OrderItem;
import com.uade.keepstar.entity.Product;
import com.uade.keepstar.entity.User;
import com.uade.keepstar.entity.dto.OrderRequest;
import com.uade.keepstar.entity.dto.OrderResponse;
import com.uade.keepstar.entity.dto.OrdenItemResquest;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.repository.OrderRepository;
import com.uade.keepstar.repository.ProductRepository;
import com.uade.keepstar.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.Builder;
@Builder

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public List<OrderResponse> getOrder() {
        return orderRepository.findAll().stream().map(OrderResponse::new).collect(Collectors.toList());
    }

    @Override
    public OrderResponse getIDList(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
        return new OrderResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse crearOrder(OrderRequest request)
            throws ProductNotFoundException, UserNotFoundException {

        // 1) Usuario
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        // 2) Productos solicitados
        List<Long> ids = request.getItems()
                .stream()
                .map(OrdenItemResquest::getProductId)
                .collect(Collectors.toList());

        List<Product> products = productRepository.findAllById(ids);
        if (products.size() != ids.size()) {
            // al menos un id no existe
            throw new ProductNotFoundException();
        }

        // 3) Crear Order + OrderItems (respetando cantidades del request)
        Order order = Order.builder().user(user.get()).items(new ArrayList<>()).build();

        for (OrdenItemResquest it : request.getItems()) {
            Product p = products.stream()
                    .filter(pr -> pr.getId().equals(it.getProductId())).findFirst()
                    .orElseThrow(ProductNotFoundException::new);

            OrderItem oi = new OrderItem(p, it.getQuantity() == null ? 1 : it.getQuantity());
            oi.setOrder(order);            // importante para la relación bidireccional
            order.getItems().add(oi);
        }

        // 4) Persistir y devolver DTO
        Order saved = orderRepository.save(order);
        return new OrderResponse(saved);
    }
}


