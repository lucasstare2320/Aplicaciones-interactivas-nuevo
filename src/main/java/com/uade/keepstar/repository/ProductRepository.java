// src/main/java/com/uade/keepstar/repository/ProductRepository.java
package com.uade.keepstar.repository;

import com.uade.keepstar.entity.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

    Optional<Product> findByIdAndActiveTrue(Long id);

    @Query("""
           SELECT p
           FROM Product p
           WHERE p.active = true
             AND (:minPrice IS NULL OR p.price >= :minPrice)
             AND (:maxPrice IS NULL OR p.price <= :maxPrice)
             AND (:categoryId IS NULL OR p.category.id = :categoryId)
           """)
    List<Product> findByOptionalFilters(@Param("minPrice") Double minPrice,
                                        @Param("maxPrice") Double maxPrice,
                                        @Param("categoryId") Long categoryId);

    Optional<Product> findByNameIgnoreCaseAndCategory_Id(String name, Long categoryId);
}
