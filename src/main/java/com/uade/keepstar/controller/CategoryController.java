package com.uade.keepstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.keepstar.entity.dto.CategoryRequest;
import com.uade.keepstar.entity.dto.CategoryResponse;
import com.uade.keepstar.entity.dto.ProductResponse;
import com.uade.keepstar.service.CategoryService;



@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

        @GetMapping
        public List<CategoryResponse> getCategory(){
        return categoryService.getCategory();
        }
        @PostMapping
        public CategoryResponse registerUser(@RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
        }
        @GetMapping("/{id}")
        public CategoryResponse getIDList(@PathVariable Long id){
        return categoryService.getIDList(id);
        }
    }