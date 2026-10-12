package com.example.blogging.blog.controller;

import com.example.blogging.blog.payload.ApiResponse;
import com.example.blogging.blog.payload.PostDto;
import com.example.blogging.blog.payload.PostResponse;
import com.example.blogging.blog.service.PostService;
import com.example.blogging.blog.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto dto, @PathVariable int userId, @PathVariable int categoryId) {

        PostDto dto1 = postService.createPost(dto, userId,categoryId);
        return new ResponseEntity<>(dto1, HttpStatus.CREATED);

    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getByCategory(@PathVariable int categoryId) {
        List<PostDto> dtos = postService.getByCategory(categoryId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getByUser(@PathVariable int userId) {
        List<PostDto> dtos = postService.getByUser(userId);
        return ResponseEntity.ok(dtos);
    }

//    @GetMapping
//    public ResponseEntity<List<PostDto>> getAllPost(@RequestParam(name = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
//                                                    @RequestParam(name = "pageSize", required = true) Integer pageSize) {
//        List<PostDto> dtos = postService.getAllPost(pageNumber, pageSize);
//        return ResponseEntity.ok(dtos);
//    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                   @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                   @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                   @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
                                                   ) {
        PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable int postId) {
        PostDto dto = postService.getPost(postId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updateDto(@Valid @RequestBody PostDto dto,@PathVariable int postId) {
        PostDto dto1 = postService.updateDto(dto, postId);
        return ResponseEntity.ok(dto1);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);

    }

    @GetMapping("search/{keyword}")
    public ResponseEntity<List<PostDto>> searchByKeyword(@PathVariable("keyword") String key){
        List<PostDto> dtos = postService.searchByKeyword("%"+key+"%");
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

}
