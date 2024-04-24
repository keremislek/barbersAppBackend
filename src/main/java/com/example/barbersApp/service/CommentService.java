package com.example.barbersApp.service;

import java.util.List;
import java.util.Optional;

import com.example.barbersApp.entities.Comment;
import com.example.barbersApp.request.CommentCreateRequest;
import com.example.barbersApp.request.CommentUpdateRequest;
import com.example.barbersApp.response.CommentResponse;

public interface CommentService {


    List<CommentResponse> getAllCommentWithsParam(Optional<Long> customerId,Optional<Long> barberId);

    CommentResponse getOneCommentById(Long commentId);

    CommentResponse createOneComment(CommentCreateRequest request);

    CommentResponse updateOneComment(Long commentId, CommentUpdateRequest request);

    void deleteComment(Long commentId);

    List<CommentResponse> getAllCommentByBarber(Optional<Long> barberId);

}
