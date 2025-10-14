package com.uade.keepstar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.keepstar.entity.Category;

public interface CategoryRepository extends JpaRepository <Category , Long>{
    public Optional<Category> findByName(String name);
}
