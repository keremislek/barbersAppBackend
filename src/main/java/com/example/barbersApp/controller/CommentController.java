package com.example.barbersApp.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.barbersApp.request.CommentCreateRequest;
import com.example.barbersApp.request.CommentUpdateRequest;
import com.example.barbersApp.response.CommentResponse;
import com.example.barbersApp.service.CommentService;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    @GetMapping
    public List<CommentResponse> getAllComments(@RequestParam Optional<Long> customerId, @RequestParam Optional<Long> barberId){
        return commentService.getAllCommentWithsParam(customerId,barberId);
    }

    @GetMapping("/barber")
    public List<CommentResponse> getAllCommentByBarber(@RequestParam Optional<Long> barberId){
        return commentService.getAllCommentByBarber(barberId);
    } 

    @GetMapping("/{commentId}")
    public CommentResponse getOneComment(@PathVariable Long commentId){
        return commentService.getOneCommentById(commentId);
    } 

    @PostMapping
    public ResponseEntity<CommentResponse> createOneComment(@RequestBody CommentCreateRequest request){
        
        return ResponseEntity.ok(commentService.createOneComment(request));
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentResponse> updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request){
        return ResponseEntity.ok(commentService.updateOneComment(commentId,request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body("Comment deleted with id: "+commentId);
    }
}
