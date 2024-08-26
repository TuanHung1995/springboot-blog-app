package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<Comment> comments;
    
}
