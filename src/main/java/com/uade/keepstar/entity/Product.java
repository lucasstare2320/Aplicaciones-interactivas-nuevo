package com.uade.keepstar.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
@Entity
@Data
@Builder

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private float price;
    @Column
    private int stock;
    @Column
    private boolean active;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "seller_id", nullable = false)
    private User seller;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "category_id", nullable = false)
    private Category category;



}