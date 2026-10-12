package com.example.blogging.blog.service.impl;

import com.example.blogging.blog.entity.Category;
import com.example.blogging.blog.exception.ResourceNotFoundException;
import com.example.blogging.blog.payload.CategoryDto;
import com.example.blogging.blog.repository.CategoryRepository;
import com.example.blogging.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.mapper.map(categoryDto, Category.class);
        Category saved = repository.save(category);
        return this.mapper.map(saved, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", "Id ", categoryId));
        category.setCategoryName(categoryDto.getName());
        Category saved = repository.save(category);
        return this.mapper.map(saved, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", "Id ", categoryId));
        repository.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", "Id ", categoryId));
        return this.mapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<CategoryDto> dtos = repository.findAll()
                .stream()
                .map(category-> this.mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        return dtos;
    }
}
