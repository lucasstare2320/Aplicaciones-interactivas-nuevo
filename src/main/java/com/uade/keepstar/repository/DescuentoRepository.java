package com.uade.keepstar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.keepstar.entity.Discount;


@Repository
public interface DescuentoRepository extends JpaRepository <Discount, Long> {
    Optional<Discount> findByCode(String code);
}
