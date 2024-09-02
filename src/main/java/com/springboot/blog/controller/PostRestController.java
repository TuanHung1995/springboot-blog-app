package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstrains;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    // Create new post rest api
    @PostMapping()
    public ResponseEntity<PostDto> createNewPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // Get all posts rest api
    @GetMapping()
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    // Get post by id rest api
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Update post rest api
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") long id,
                                              @Valid @RequestBody PostDto postDto) {
        PostDto responsePost = postService.updatePost(postDto,id);
        return new ResponseEntity<>(responsePost, HttpStatus.OK);
    }

    // Delete post rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }

    // Get all posts with pagination rest api
    @GetMapping("/page")
    public PostResponse getAllPostPage(@RequestParam(name = "page", defaultValue = AppConstrains.DEFAULT_PAGE_NUMBER, required = false) int page,
                                       @RequestParam(name = "size", defaultValue = AppConstrains.DEFAULT_PAGE_SIZE, required = false) int size,
                                       @RequestParam(name = "sortBy", defaultValue = AppConstrains.DEFAULT_SORT_BY, required = false) String sortBy,
                                       @RequestParam(name = "sortOrder", defaultValue = AppConstrains.DEFAULT_SORT_DIRECTION, required = false) String sortOrder) {
        return postService.getAllPostPage(page, size, sortBy, sortOrder);
    }

    // Get posts by category id rest api
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable("id") Long categoryId) {
        List<PostDto> post = postService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(post);
    }

}
