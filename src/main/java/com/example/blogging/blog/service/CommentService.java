package com.example.blogging.blog.service;

import com.example.blogging.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto writeComment(CommentDto dto, int userId, int postId);
    CommentDto updateComment(CommentDto dto, int commentId);
    void deleteComment(int commentId);
    List<CommentDto> commentByUser(int userId);
    List<CommentDto> commentByPost(int postId);
    CommentDto getComment(int commentId);
    List<CommentDto> getAllComment();

}
