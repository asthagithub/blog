package com.example.blogging.blog.service;

import com.example.blogging.blog.entity.Post;
import com.example.blogging.blog.payload.PostDto;
import com.example.blogging.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    //create
    PostDto createPost(PostDto dto, int userId, int categoryId);

    //delete
    void deletePost(int postId);

    //update
    PostDto updateDto(PostDto dto, int postId);

    //get
    PostDto getPost(int postId);

    //getAll
   // List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    //getByUser
    List<PostDto> getByUser(int userId);

    //getByCategory
    List<PostDto> getByCategory(int categoryId);

    List<PostDto> searchByKeyword(String key);
}
