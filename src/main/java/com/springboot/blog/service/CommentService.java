package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto); // Create new comment
    List<CommentDto> getCommentsByPostId(long postId); // Get all comments by post id
    CommentDto getCommentById(long postId, long commentId); // Get comment by post id and comment id
    CommentDto updateComment(long postId, long commentId, CommentDto commentDto); // Update comment by post id and comment id
    void deleteCommentById(long postId, long commentId); // Delete comment by post id and comment id
}
