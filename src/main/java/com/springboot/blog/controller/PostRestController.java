package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstrains;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Operation(
            summary = "Create new post",
            description = "Create new post and save to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED)"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<PostDto> createNewPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // Get all posts rest api
    @Operation(
            summary = "Get all posts",
            description = "Get all posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping()
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    // Get post by id rest api
    @Operation(
            summary = "Get post by id",
            description = "Get post by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS)"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Update post rest api
    @Operation(
            summary = "Update post",
            description = "Update post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS)"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") long id,
                                              @Valid @RequestBody PostDto postDto) {
        PostDto responsePost = postService.updatePost(postDto,id);
        return new ResponseEntity<>(responsePost, HttpStatus.OK);
    }

    // Delete post rest api
    @Operation(
            summary = "Delete post",
            description = "Delete post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS)"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }

    // Get all posts with pagination rest api
    @Operation(
            summary = "Get all posts with pagination",
            description = "Get all posts with pagination"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS)"
    )
    @GetMapping("/page")
    public PostResponse getAllPostPage(@RequestParam(name = "page", defaultValue = AppConstrains.DEFAULT_PAGE_NUMBER, required = false) int page,
                                       @RequestParam(name = "size", defaultValue = AppConstrains.DEFAULT_PAGE_SIZE, required = false) int size,
                                       @RequestParam(name = "sortBy", defaultValue = AppConstrains.DEFAULT_SORT_BY, required = false) String sortBy,
                                       @RequestParam(name = "sortOrder", defaultValue = AppConstrains.DEFAULT_SORT_DIRECTION, required = false) String sortOrder) {
        return postService.getAllPostPage(page, size, sortBy, sortOrder);
    }

    // Get posts by category id rest api
    @Operation(
            summary = "Get posts by category id",
            description = "Get posts by category id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS)"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable("id") Long categoryId) {
        List<PostDto> post = postService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(post);
    }

}
