package com.example.blogging.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    private String title;

    private String content;

    private String imageUrl;

    private Date date;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comment = new HashSet<>();




}
