package com.uade.keepstar.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Blob;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image_table")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private Blob image;

    @Column(name = "image_name")
    private String imageName;
}
