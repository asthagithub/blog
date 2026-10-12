package com.example.blogging.blog.service.impl;

import com.example.blogging.blog.entity.Comment;
import com.example.blogging.blog.entity.Post;
import com.example.blogging.blog.entity.User;
import com.example.blogging.blog.exception.ResourceNotFoundException;
import com.example.blogging.blog.payload.CommentDto;
import com.example.blogging.blog.repository.CommentRepository;
import com.example.blogging.blog.repository.PostRepository;
import com.example.blogging.blog.repository.UserRepository;
import com.example.blogging.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public CommentDto writeComment(CommentDto dto, int userId, int postId) {
        Comment comment = this.mapper.map(dto, Comment.class);
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post id", postId));
        comment.setCommentContent(dto.getContent());
        comment.setUser(user);
        comment.setPost(post);
        Comment saved = commentRepository.save(comment);
        
        return this.mapper.map(saved, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto dto, int commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "comment id", commentId));
        comment.setCommentContent(dto.getContent());
        Comment savedComment = commentRepository.save(comment);
        return this.mapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> commentByUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userid", userId));
        return commentRepository.findByUser(user).stream().map(comment-> this.mapper.map(comment, CommentDto.class)).collect(Collectors.toList());

    }

    @Override
    public List<CommentDto> commentByPost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id", postId));
        return commentRepository.findByPost(post).stream().map(comment -> this.mapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getComment(int commentId) {
        Comment comment=  commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment ", "comment id", commentId));
        return this.mapper.map(comment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getAllComment() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> this
                .mapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }
}
