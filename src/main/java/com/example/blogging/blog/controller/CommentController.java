package com.example.blogging.blog.controller;

import com.example.blogging.blog.payload.ApiResponse;
import com.example.blogging.blog.payload.CommentDto;
import com.example.blogging.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/write/user/{userId}/post/{postId}")
    public ResponseEntity<CommentDto> writeComment(@RequestBody CommentDto dto, @PathVariable int userId, @PathVariable int postId) {
        CommentDto dto1 = this.commentService.writeComment(dto, userId, postId);
        return new ResponseEntity<>(dto1,HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto dto, @PathVariable("commentId") int id) {
        CommentDto dto1 = this.commentService.updateComment(dto, id);
        return ResponseEntity.ok(dto1);
    }

   @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successfully", true), HttpStatus.OK);

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> commentByUser(@PathVariable int userId) {
        return ResponseEntity.ok(this.commentService.commentByUser(userId));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> commentByPost(@PathVariable int postId) {
        return ResponseEntity.ok(this.commentService.commentByPost(postId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable int commentId) {
        return ResponseEntity.ok(this.commentService.getComment(commentId));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComment() {
        return ResponseEntity.ok(this.commentService.getAllComment());
    }
}
