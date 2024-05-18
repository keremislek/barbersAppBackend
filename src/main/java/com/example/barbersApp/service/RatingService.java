package com.example.barbersApp.service;

import java.util.List;
import java.util.Optional;

import com.example.barbersApp.request.RatingCreateRequest;
import com.example.barbersApp.request.RatingUpdateRequest;
import com.example.barbersApp.response.RatingCreateResponse;
import com.example.barbersApp.response.RatingResponse;

public interface RatingService {

    RatingCreateResponse createOneRating(RatingCreateRequest request);

    RatingCreateResponse updateOneComment( RatingUpdateRequest request,Long ratingId);

    List<RatingResponse> getAllRatingWithParam(Optional<Long> customerId, Optional<Long> barberId);

}
