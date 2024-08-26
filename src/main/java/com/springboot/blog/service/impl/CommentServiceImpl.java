package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.BadLocationException;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        // Convert CommentDto to Comment
        Comment comment = convertCommentDtoToComment(commentDto);

        // Retrieve Post from the database
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Set Post for the comment
        comment.setPost(post);

        // Save Comment to database
        Comment newComment = commentRepository.save(comment);

        // Convert Comment to CommentDto
        return convertCommentToCommentDto(newComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

            // Retrieve Comments from the database
            List<Comment> comments = commentRepository.findByPostId(postId);

            // Convert Comments to CommentDto
            return comments.stream().map(this::convertCommentToCommentDto).toList();
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

        // Retrieve Post from the database
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve Comment from the database
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        return convertCommentToCommentDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

        // Retrieve Post from the database
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve Comment from the database
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        // Update Comment
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepository.save(comment);

        return convertCommentToCommentDto(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {

        // Retrieve Post from the database
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve Comment from the database
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        // Delete Comment
        commentRepository.delete(comment);
    }

    // Convert CommentDto to Comment
    private Comment convertCommentDtoToComment(CommentDto commentDto) {
//        Comment comment = new Comment();
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return mapper.map(commentDto, Comment.class);
    }

    // Convert Comment to CommentDto
    private CommentDto convertCommentToCommentDto(Comment comment) {
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return mapper.map(comment, CommentDto.class);
    }
}
