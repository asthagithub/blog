package com.example.blogging.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
