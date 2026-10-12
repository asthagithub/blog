package com.example.blogging.blog.service.impl;

import com.example.blogging.blog.entity.Category;
import com.example.blogging.blog.entity.Post;
import com.example.blogging.blog.entity.User;
import com.example.blogging.blog.exception.ResourceNotFoundException;
import com.example.blogging.blog.payload.PostDto;
import com.example.blogging.blog.payload.PostResponse;
import com.example.blogging.blog.repository.CategoryRepository;
import com.example.blogging.blog.repository.PostRepository;
import com.example.blogging.blog.repository.UserRepository;
import com.example.blogging.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostImpl implements PostService {

    @Autowired
    PostRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public PostDto createPost(PostDto dto, int userId, int categoryId) {

        Post post = this.mapper.map(dto, Post.class);
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        post.setDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post saved = repository.save(post);
        return this.mapper.map(saved, PostDto.class);
    }

    @Override
    public void deletePost(int postId) {
        Post post= repository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id",postId));
        repository.delete(post);

    }

    @Override
    public PostDto updateDto(PostDto dto, int postId) {
        Post post = repository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
        post.setImageUrl(dto.getImageUrl());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        Post saved = repository.save(post);
        return this.mapper.map(saved, PostDto.class);
    }

    @Override
    public PostDto getPost(int postId) {
        Post post = repository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
        return this.mapper.map(post, PostDto.class);
    }

//    @Override
//    public List<PostDto> getAllPost(Integer pageNumber, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page page = repository.findAll(pageable);
//        List<Post> posts = page.getContent();
//        return posts.stream().map(post-> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
//
//    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = null;
        sort = sortOrder.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page page = repository.findAll(pageable);
       // List<Post> posts = page.getContent();

        PostResponse response = new PostResponse();
        response.setContent(page.getContent().stream().map(post -> this.mapper.map(post, PostDto.class)).toList());
        response.setLast(page.isLast());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setPageSize(page.getSize());
        response.setPageNumber(page.getNumber());

        return response;

    }

    @Override
    public List<PostDto> getByUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user id",userId));
        List<Post> posts = repository.findByUser(user);
        return posts.stream().map(post -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());

    }

    @Override
    public List<PostDto> getByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));
        List<Post> posts = repository.findByCategory(category);
        return posts.stream().map(post -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());

    }

    @Override
    public List<PostDto> searchByKeyword(String key) {
        List<Post> posts = repository.searchByKeyword(key);
        return posts.stream().map(post-> this.mapper.map(post, PostDto.class)).collect(Collectors.toUnmodifiableList());
    }
}
