package com.uade.keepstar.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.keepstar.entity.Category;

import com.uade.keepstar.entity.dto.CategoryRequest;
import com.uade.keepstar.entity.dto.CategoryResponse;

import com.uade.keepstar.repository.CategoryRepository;

@Service

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryRepository.save(Category.builder()
        .name(request.getName()).build()
        );
        return new CategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getCategory() {

        return categoryRepository.findAll().stream().map(category -> new CategoryResponse(category)).toList();
        
    }

    @Override
    public CategoryResponse getIDList(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return new CategoryResponse(category.get());

    }
    
}