package com.example.blogging.blog.controller;

import com.example.blogging.blog.entity.Category;
import com.example.blogging.blog.payload.ApiResponse;
import com.example.blogging.blog.payload.CategoryDto;
import com.example.blogging.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService service;

    @PreAuthorize("hasRole('IT')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto dto = service.createCategory(categoryDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer id) {
        CategoryDto dto = service.updateCategory(categoryDto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
        service.deleteCategory(categoryId);
        ApiResponse response = new ApiResponse(String.format("Category with id: %s deleted successfully", categoryId), true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
        CategoryDto dto = service.getCategory(categoryId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> dtos = service.getAllCategory();
        return ResponseEntity.ok(dtos);
    }
}
