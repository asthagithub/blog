package com.example.blogging.blog.payload;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@JsonPropertyOrder({
        "id",
        "title",
        "content",
        "imageUrl",
        "date",
        "category",
        "user"
})
@Setter
@Getter
@NoArgsConstructor
public class PostDto {

    private int id;

    @NotEmpty
    private String title;

    private String content;

    private String imageUrl;

    private Date date;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comment= new HashSet<>();
}
