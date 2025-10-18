package com.uade.keepstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.keepstar.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    // Para contar imágenes de un producto (por si lo seguís usando en algún lugar)
    long countByProduct_Id(Long productId);

    // Para obtener las ENTIDADES Image de un producto, ordenadas por id
    List<Image> findByProduct_IdOrderById(Long productId);
}
