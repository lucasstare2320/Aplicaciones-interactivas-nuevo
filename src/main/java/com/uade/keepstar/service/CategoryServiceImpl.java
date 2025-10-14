package com.uade.keepstar.service;


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
    
}