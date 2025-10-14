package com.uade.keepstar.entity.dto;

import com.uade.keepstar.entity.Category;

import lombok.Data;

@Data

public class CategoryResponse {

    private String name;

    private long id;

    public CategoryResponse (Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}


