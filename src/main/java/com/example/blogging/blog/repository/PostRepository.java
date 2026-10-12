package com.example.blogging.blog.repository;


import com.example.blogging.blog.entity.Category;
import com.example.blogging.blog.entity.Post;
import com.example.blogging.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query("select p from Post p where p.title like :key")
    List<Post> searchByKeyword(String key);             //without @Param also works
}
