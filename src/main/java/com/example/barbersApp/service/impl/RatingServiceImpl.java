package com.example.barbersApp.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.entities.Customer;
import com.example.barbersApp.entities.Rating;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.repository.CustomerRepository;
import com.example.barbersApp.repository.RatingRepository;
import com.example.barbersApp.request.RatingCreateRequest;
import com.example.barbersApp.request.RatingUpdateRequest;
import com.example.barbersApp.response.RatingCreateResponse;
import com.example.barbersApp.response.RatingResponse;
import com.example.barbersApp.service.RatingService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RatingServiceImpl implements RatingService{

    RatingRepository ratingRepository;
    BarberRepository barberRepository;
    CustomerRepository customerRepository;
    
    public RatingServiceImpl(RatingRepository ratingRepository,BarberRepository barberRepository, CustomerRepository customerRepository){
        this.ratingRepository=ratingRepository;
        this.barberRepository=barberRepository;
        this.customerRepository=customerRepository;
    } 

    @Override
    public RatingCreateResponse createOneRating(RatingCreateRequest request) {
        Barber barber=barberRepository.findById(request.getBarberId()).orElseThrow(()->
        new EntityNotFoundException("Barber not found with id: "+request.getBarberId()));
        Customer customer= customerRepository.findById(request.getCustomerId()).orElseThrow(()->
        new EntityNotFoundException("Customer not found with id: "+request.getCustomerId()));
        
        Rating ratingToSave = new Rating();
        ratingToSave.setBarber(barber);
        ratingToSave.setCustomer(customer);
        ratingToSave.setRate(request.getRate());
        
        Rating createdRating = ratingRepository.save(ratingToSave);

        RatingCreateResponse ratingCreateResponse=RatingCreateResponse.builder()
        .barberName(createdRating.getBarber().getBarberName())
        .customerName(createdRating.getCustomer().getFirstName())
        .rate(createdRating.getRate())
        .build();

        return ratingCreateResponse;
        
    }

    @Override
    public RatingCreateResponse updateOneComment(RatingUpdateRequest request,Long ratingId) {
        
        Rating rating=ratingRepository.findById(ratingId).orElseThrow(()->new EntityNotFoundException("Rating not found by id: "+ratingId));
        rating.setRate(request.getRate());
        ratingRepository.save(rating);

        RatingCreateResponse ratingCreateResponse=RatingCreateResponse.builder()
        .barberName(rating.getBarber().getBarberName())
        .customerName(rating.getCustomer().getFirstName())
        .rate(rating.getRate())
        .build();
        
        return ratingCreateResponse;
    }

    @Override
    public List<RatingResponse> getAllRatingWithParam(Optional<Long> customerId, Optional<Long> barberId) {
       List<Rating> ratings;
       if(customerId.isPresent()&&barberId.isPresent()){
        ratings=ratingRepository.findByCustomerIdAndBarberId(customerId,barberId);
       } 
       else if(customerId.isPresent()){
        ratings=ratingRepository.findByCustomerId(customerId);
       }
       else if(barberId.isPresent()){
        ratings=ratingRepository.findByBarberId(barberId);
       }
       else{
        ratings=ratingRepository.findAll();
       }
       return ratings.stream().map(rating->RatingResponse.builder()
       .ratingId(rating.getId())
       .barberId(rating.getBarber().getId())
       .barberName(rating.getBarber().getBarberName())
       .customerId(rating.getCustomer().getId())
       .customerName(rating.getCustomer().getFirstName())
       .rate(rating.getRate())
       .build()).collect(Collectors.toList());
       
    }

   
}
