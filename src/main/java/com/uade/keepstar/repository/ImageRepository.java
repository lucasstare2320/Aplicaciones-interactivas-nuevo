package com.uade.keepstar.repository;

import com.uade.keepstar.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    long countByProduct_Id(Long productId);

    List<Image> findByProduct_IdOrderById(Long productId);
}
