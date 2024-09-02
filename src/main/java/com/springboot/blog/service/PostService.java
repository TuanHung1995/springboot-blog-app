package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto); // Create new post
    List<PostDto> getAllPosts(); // Get all posts
    PostDto getPostById(long id); // Get post by id
    PostDto updatePost(PostDto postDto, long id); // Update post
    void deletePost(long id); // Delete post
    PostResponse getAllPostPage(int page, int size, String sort, String sortOrder); // Get all posts with pagination
    List<PostDto> getPostsByCategoryId(Long categoryId); // Get posts by category id
}
