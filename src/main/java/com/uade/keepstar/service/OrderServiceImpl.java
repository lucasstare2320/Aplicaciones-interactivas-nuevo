package com.uade.keepstar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.keepstar.entity.Order;
import com.uade.keepstar.entity.OrderItem;
import com.uade.keepstar.entity.OrderStatus;
import com.uade.keepstar.entity.Product;
import com.uade.keepstar.entity.User;
import com.uade.keepstar.entity.dto.OrderRequest;
import com.uade.keepstar.entity.dto.OrderResponse;
import com.uade.keepstar.entity.dto.OrdenItemResquest;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.exceptions.ProductWithoutException;
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.repository.OrderRepository;
import com.uade.keepstar.repository.ProductRepository;
import com.uade.keepstar.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public List<OrderResponse> getOrder() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getIDList(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        return new OrderResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse crearOrder(OrderRequest request)
            throws ProductNotFoundException, UserNotFoundException, ProductWithoutException {

        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        List<Long> requestedIds = request.getItems()
                .stream()
                .map(OrdenItemResquest::getProductId)
                .toList();

        List<Product> products = productRepository.findAllById(requestedIds);
        if (products.size() != requestedIds.size()) {
            throw new ProductNotFoundException();
        }

        Map<Long, Product> productsById = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        List<OrdenItemResquest> itemsConStockInsuficiente = request.getItems()
                .stream()
                .filter(itemReq -> {
                    Product product = productsById.get(itemReq.getProductId());
                    int quantity = itemReq.getQuantity() == null ? 1 : itemReq.getQuantity();
                    return product.getStock() < quantity; 
                })
                .toList();

        if (!itemsConStockInsuficiente.isEmpty()) {
 
            throw new ProductWithoutException();
        }


        for (OrdenItemResquest itemReq : request.getItems()) {
            Product product = productsById.get(itemReq.getProductId());
            int quantity = itemReq.getQuantity() == null ? 1 : itemReq.getQuantity();

            int newStock = product.getStock() - quantity;
            product.setStock(newStock);
            product.setActive(newStock > 0);
        }

        Order order = Order.builder()
                .user(user.get())
                .items(new ArrayList<>())
                .status(OrderStatus.PENDING)
                .build();


        for (OrdenItemResquest itemReq : request.getItems()) {
            Product product = productsById.get(itemReq.getProductId());
            int quantity = itemReq.getQuantity() == null ? 1 : itemReq.getQuantity();

            OrderItem orderItem = new OrderItem(product, quantity);
            orderItem.setOrder(order);       
            order.getItems().add(orderItem);
        }

        productRepository.saveAll(products);
        Order saved = orderRepository.save(order);

        return new OrderResponse(saved);
    }
}
