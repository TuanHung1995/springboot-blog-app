package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // Convert PostDto to Post
        Post post = convertPostDtoToPost(postDto);

        // Save Post to database
        Post newPost = postRepository.save(post);

        // Convert Post to PostDto
        return convertPostToPostDto(newPost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(this::convertPostToPostDto).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post =  postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return convertPostToPostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {

        // Retrieve Post from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        // Save Post to database
        Post updatedPost = postRepository.save(post);
        return convertPostToPostDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.deleteById(id);
    }

    @Override
    public PostResponse getAllPostPage(int page, int size, String sort, String sortOrder) {

        Sort sortOr = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sort).ascending() : Sort.by(sort).descending();

        // Create Pageable instance
        Pageable pageable = PageRequest.of(page, size, sortOr);

        // All posts with pagination
        Page<Post> posts = postRepository.findAll(pageable);

        // Get content of Page
        List<Post> listPost = posts.getContent();

        List<PostDto> content =  listPost.stream().map(this::convertPostToPostDto).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPage(posts.getNumber());
        postResponse.setSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    // Convert Post to PostDto
    private PostDto convertPostToPostDto(Post post) {
        //        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return mapper.map(post, PostDto.class);
    }

    // Convert PostDto to Post
    private Post convertPostDtoToPost(PostDto postDto) {
        //        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return mapper.map(postDto, Post.class);
    }

}
