package com.uade.keepstar.service;

import java.util.List;

import com.uade.keepstar.entity.dto.CategoryRequest;
import com.uade.keepstar.entity.dto.CategoryResponse;

public interface CategoryService {

    public CategoryResponse createCategory(CategoryRequest request);

    public List<CategoryResponse> getCategory();

    public CategoryResponse getIDList(Long id);
       

}
