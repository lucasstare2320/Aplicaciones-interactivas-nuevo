package com.uade.keepstar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "discounts", uniqueConstraints = {
        @UniqueConstraint(name = "uk_discounts_code", columnNames = "code")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60, unique = true)
    private String code;

    // porcentaje entero: 10 = 10%
    @Column(nullable = false)
    private int porcentaje;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
