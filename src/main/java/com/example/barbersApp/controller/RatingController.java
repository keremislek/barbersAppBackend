package com.example.barbersApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.barbersApp.request.RatingCreateRequest;
import com.example.barbersApp.request.RatingUpdateRequest;
import com.example.barbersApp.response.RatingCreateResponse;
import com.example.barbersApp.service.RatingService;

@RestController
@RequestMapping("/ratings")
@CrossOrigin
public class RatingController {

    RatingService ratingService;

    public RatingController(RatingService ratingService){
        this.ratingService=ratingService;
    }

    @PostMapping("/create")
    public ResponseEntity<RatingCreateResponse> createOneRating(@RequestBody RatingCreateRequest request ){
        return ResponseEntity.ok(ratingService.createOneRating(request));
    }

    @PutMapping("/update/{ratingId}")
    public ResponseEntity<RatingCreateResponse> updateOneRating(@RequestBody RatingUpdateRequest request, @PathVariable Long ratingId){
        return ResponseEntity.ok(ratingService.updateOneComment(request,ratingId));
    }
}
