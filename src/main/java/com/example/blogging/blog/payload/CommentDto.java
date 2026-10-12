package com.example.blogging.blog.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentDto {
    public int id;
    public String content;
}
