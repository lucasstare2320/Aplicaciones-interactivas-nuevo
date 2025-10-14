package com.uade.keepstar.entity;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor


public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int quantity;

    @ManyToAny
    @JoinColumn (name = "product_id", nullable = false)
    private Product product;

    @ManyToAny
    @JoinColumn (name = "order_id", nullable = false)
    private Order order;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}
