package com.example.blogging.blog.repository;

import com.example.blogging.blog.entity.Comment;
import com.example.blogging.blog.entity.Post;
import com.example.blogging.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByUser(User user);
    List<Comment> findByPost(Post post);
}
