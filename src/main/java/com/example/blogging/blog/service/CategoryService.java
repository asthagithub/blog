package com.example.blogging.blog.service;


import com.example.blogging.blog.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    void deleteCategory(Integer categoryId);
    CategoryDto getCategory(Integer categoryId);
    List<CategoryDto> getAllCategory();
}
