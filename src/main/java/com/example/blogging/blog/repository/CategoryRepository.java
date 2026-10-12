package com.example.blogging.blog.repository;

import com.example.blogging.blog.entity.Category;
import com.example.blogging.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
