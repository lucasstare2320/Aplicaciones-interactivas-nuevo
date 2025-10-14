package com.uade.keepstar.service;

import com.uade.keepstar.entity.dto.CategoryRequest;
import com.uade.keepstar.entity.dto.CategoryResponse;

public interface CategoryService {

    public CategoryResponse createCategory(CategoryRequest request);
       

}
