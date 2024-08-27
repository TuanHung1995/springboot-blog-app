package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title must be at least 2 characters long")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description must be at least 10 characters long")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
    
}
