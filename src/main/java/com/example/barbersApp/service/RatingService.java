package com.example.barbersApp.service;

import com.example.barbersApp.request.RatingCreateRequest;
import com.example.barbersApp.request.RatingUpdateRequest;
import com.example.barbersApp.response.RatingCreateResponse;

public interface RatingService {

    RatingCreateResponse createOneRating(RatingCreateRequest request);

    RatingCreateResponse updateOneComment( RatingUpdateRequest request,Long ratingId);

}
