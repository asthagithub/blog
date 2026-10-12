package com.example.blogging.blog.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PostResponse {
    public int pageNumber;
    public int pageSize;
    public List<PostDto> content;
    public long totalElements;
    public int totalPages;
    public boolean last;
}
