package com.example.barbersApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{

    List<Comment> findByCustomerIdAndBarberId(Optional<Long> customerId, Optional<Long> barberId);

    List<Comment> findByCustomerId(Optional<Long> customerId);

    List<Comment> findByBarberId(Optional<Long> barberId);

}
