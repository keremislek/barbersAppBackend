package com.example.barbersApp.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.entities.Comment;
import com.example.barbersApp.entities.Customer;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.repository.CommentRepository;
import com.example.barbersApp.repository.CustomerRepository;
import com.example.barbersApp.request.CommentCreateRequest;
import com.example.barbersApp.request.CommentUpdateRequest;
import com.example.barbersApp.response.CommentResponse;

import com.example.barbersApp.service.CommentService;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;
    private CustomerRepository customerRepository;
    private BarberRepository barberRepository;

    public CommentServiceImpl(CommentRepository commentRepository,CustomerRepository customerRepository,BarberRepository barberRepository){
        this.commentRepository=commentRepository;
        this.customerRepository=customerRepository;
        this.barberRepository=barberRepository;
    }

    @Override
    public List<CommentResponse> getAllCommentWithsParam(Optional<Long> customerId, Optional<Long> barberId) {
        List<Comment> comments;
        if(customerId.isPresent() && barberId.isPresent()){
            comments= commentRepository.findByCustomerIdAndBarberId(customerId,barberId);
        }
        else if(customerId.isPresent()){
            comments=commentRepository.findByCustomerId(customerId);
        }
        else if(barberId.isPresent()){
            comments=commentRepository.findByBarberId(barberId);
        }else{
            comments=commentRepository.findAll();
        }

        return comments.stream().map(comment->CommentResponse.builder()
        .commentId(comment.getId())
        .barberId(comment.getBarber().getId())
        .barberName(comment.getBarber().getBarberName())
        .customerId(comment.getCustomer().getId())
        .customerName(comment.getCustomer().getFirstName())
        .text(comment.getText())
        .build()).collect(Collectors.toList());
    }

    @Override
    public CommentResponse getOneCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
        .orElseThrow(()->new EntityNotFoundException("Comment not found by id : "+commentId));

        CommentResponse commentResponse=CommentResponse.builder()
        .commentId(comment.getId())
        .barberId(comment.getBarber().getId())
        .barberName(comment.getBarber().getBarberName())
        .customerId(comment.getCustomer().getId())
        .customerName(comment.getCustomer().getFirstName())
        .text(comment.getText())
        .build();

        return commentResponse;

    }

    @Override
    @SuppressWarnings("null")
    public CommentResponse createOneComment(CommentCreateRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
        .orElseThrow(()->new EntityNotFoundException("Customer not found with id: "+request.getCustomerId()));
        Barber barber= barberRepository.findById(request.getBarberId())
        .orElseThrow(()->new EntityNotFoundException("Barber not found with id: "+request.getBarberId()));
        
        Comment commentToSave= new Comment();
        commentToSave.setBarber(barber);
        commentToSave.setCustomer(customer);
        commentToSave.setText(request.getText());
        
        
        Comment createdComment=commentRepository.save(commentToSave);

        CommentResponse commentResponse=CommentResponse.builder()
        .commentId(createdComment.getId())
        .barberId(createdComment.getBarber().getId())
        .barberName(createdComment.getBarber().getBarberName())
        .customerId(createdComment.getCustomer().getId())
        .customerName(createdComment.getCustomer().getFirstName())
        .text(commentToSave.getText())
        .build();

        return commentResponse;
    }

    @Override
    @Transactional
    public CommentResponse updateOneComment(Long id, CommentUpdateRequest request) {
        Comment comment= commentRepository.findById(id).orElseThrow(
            ()->new EntityNotFoundException("Comment not found by id: "+id));
        comment.setText(request.getText());
        commentRepository.save(comment);

        CommentResponse commentResponse=CommentResponse.builder()
        .commentId(comment.getId())
        .barberId(comment.getBarber().getId())
        .barberName(comment.getBarber().getBarberName())
        .customerId(comment.getCustomer().getId())
        .customerName(comment.getCustomer().getFirstName())
        .text(comment.getText())
        .build();

        return commentResponse; 
    }

    @SuppressWarnings("null")
    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment=commentRepository.findById(commentId).orElseThrow(
            ()->new EntityNotFoundException("Comment not found by id : "+commentId));
        
        commentRepository.delete(comment);    
    }

    @Override
    @Transactional
    public List<CommentResponse> getAllCommentByBarber(Optional<Long> barberId) {
        

        throw new UnsupportedOperationException("Unimplemented method 'getAllCommentByBarber'");
    }

    
  

}
