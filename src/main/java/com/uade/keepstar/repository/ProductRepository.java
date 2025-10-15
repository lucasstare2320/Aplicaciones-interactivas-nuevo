package com.uade.keepstar.repository;

import com.uade.keepstar.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Optional<Product> findByNameIgnoreCaseAndCategory_Id(String name, Long categoryId);

    @Query("""
           SELECT product
           FROM Product product
           WHERE (:minPrice IS NULL OR product.price >= :minPrice)
             AND (:maxPrice IS NULL OR product.price <= :maxPrice)
             AND (:categoryId IS NULL OR product.category.id = :categoryId)
           """)
    List<Product> findByOptionalFilters(@Param("minPrice") Double minPrice,
                                        @Param("maxPrice") Double maxPrice,
                                        @Param("categoryId") Long categoryId);
}
