package com.example.barbersApp.service.impl;


import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.data.domain.Pageable;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.entities.Comment;
import com.example.barbersApp.entities.Rating;
import com.example.barbersApp.entities.Role;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.repository.CommentRepository;
import com.example.barbersApp.repository.RatingRepository;
import com.example.barbersApp.request.AuthenticationRequest;
import com.example.barbersApp.request.CreateBarberRequest;
import com.example.barbersApp.response.AuthenticationResponse;
import com.example.barbersApp.response.BarberDetailResponse;
import com.example.barbersApp.response.BarberResponse;
import com.example.barbersApp.response.BarberTop5Query;
import com.example.barbersApp.response.FamousBarbers;
import com.example.barbersApp.service.BarberService;

import jakarta.persistence.EntityNotFoundException;




@Service
public class BarberServiceImpl implements BarberService {
	
	 BarberRepository barberRepository;
	 PasswordEncoder passwordEncoder;
	 JwtService jwtService;
	 AuthenticationManager authenticationManager;
	 RatingRepository ratingRepository;
	 CommentRepository commentRepository;
	 BarberTop5Query barberTop5Query;
	 

	
	public BarberServiceImpl(BarberRepository barberRepository,PasswordEncoder passwordEncoder,JwtService jwtService,AuthenticationManager authenticationManager, RatingRepository ratingRepository,
		CommentRepository commentRepository) {
		this.barberRepository=barberRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtService=jwtService;
		this.authenticationManager=authenticationManager;
		this.ratingRepository=ratingRepository;
		this.commentRepository=commentRepository;
	}

	@Override
	public AuthenticationResponse register(CreateBarberRequest request) {
		Barber barber=Barber.builder()
		.barberName(request.getBarberName())
		.email(request.getEmail())
		.password(passwordEncoder.encode(request.getPassword()))//passwordEncoder.encode(request.getPassword()))
		.role(Role.Barber)
		.photoUrl(request.getPhotoUrl())
		.build();
		barberRepository.save(barber);
		var jwtToken=jwtService.generateToken(barber);
		
		return AuthenticationResponse.builder().token(jwtToken).id(barber.getId()).build();
	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
		);

		var user= barberRepository.findByEmail(request.getEmail()).get();
		var jwtToken=jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).id(user.getId())
		.build();
	}

	

	@Override
	public List<BarberResponse> getAllBarbers() {
		List<Barber> barbers= barberRepository.findAll();
		return barbers.stream().map(this::mapToBarberResponse).collect(Collectors.toList());
	}

	private BarberResponse mapToBarberResponse(Barber barber){
		String fullAddress = null;
        if (barber.getAddressesInfo() != null) {
            fullAddress = barber.getAddressesInfo().getFullAddress();
        }
        if (fullAddress == null) {
            fullAddress = "Bilinmeyen Adres";
        }
		
		return BarberResponse.builder().barberName(barber.getBarberName())
		.id(barber.getId())
		.photoUrl(barber.getPhotoUrl())
		.address(fullAddress).build();
	}

	@Override
	public BarberDetailResponse getBarberById(Long id) {
		var barber=barberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("barber not found by id: "+id));
		List<Rating> ratings = ratingRepository.findByBarberId(Optional.of(id));
		List<Comment> comments= commentRepository.findByBarberId(id);

		
		double avg=0;
		for (Rating rating : ratings) {
			avg+=rating.getRate();

		}
		avg=avg/ratings.size();
		
		String fullAddress = barber.getAddressesInfo() != null ? barber.getAddressesInfo().getFullAddress() : "Bilinmeyen Adres";
		

		BarberDetailResponse barberDetailResponse=BarberDetailResponse.builder()
		.id(id)
		.address(fullAddress)
		.barberName(barber.getBarberName())
		.services(barber.getServices())
		.photoUrl(barber.getPhotoUrl())
		.rate(avg)
		.commentSize(comments.size())
		.build();

		return barberDetailResponse;
		
	}

	@Override
	public BarberResponse getBarberByEmail(String email) {
		Barber barber=barberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("barber not found by email : "+email));
		BarberResponse barberResponse=BarberResponse.builder()
		.address(barber.getAddressesInfo().getFullAddress())
		.barberName(barber.getBarberName())
		.id(barber.getId())
		.build();

		return barberResponse;
	}

	@Override
	public List<FamousBarbers> getFamousBarberByRating() {
    List<Object[]> results = ratingRepository.findfamousByRating(PageRequest.of(0, 6));
    
    List<BarberTop5Query> famousBarber = new ArrayList<>();
	List<Barber> barbers= new ArrayList<>();
    for (Object[] result : results) {
        BarberTop5Query barberTop5Query = new BarberTop5Query() {
            @Override
            public Long getBarberId() {
                return (Long) result[0];
            }

            @Override
            public Double getAvgRate() {
                return (Double) result[1];
            }
        };
		
        famousBarber.add(barberTop5Query);
    }
	for (BarberTop5Query query : famousBarber) {
		Long barberId=query.getBarberId();
		Barber barber=barberRepository.findById(barberId).orElseThrow(()->new EntityNotFoundException("Barber not found with id : "+barberId));
		

		barbers.add(barber);
	}


    return mapToFamousBarbers(barbers,famousBarber);
}

private List<FamousBarbers> mapToFamousBarbers(List<Barber> barbers, List<BarberTop5Query> famousBarber){
    List<FamousBarbers> famousBarbers = new ArrayList<>();

	for (int i = 0; i < barbers.size(); i++) {
        Barber barber = barbers.get(i);
        BarberTop5Query query = famousBarber.get(i);
		
		List<Comment> comments= commentRepository.findByBarberId(barber.getId());
        
        FamousBarbers topBarber = new FamousBarbers();
        topBarber.setBarberId(barber.getId());
        topBarber.setPhotoUrl(barber.getPhotoUrl());
        topBarber.setBarberName(barber.getBarberName());
        topBarber.setBarberAddress(barber.getAddress());
        topBarber.setRate(query.getAvgRate()); // set the rate from BarberTop5Query
        topBarber.setCommentSize(comments.size());
        famousBarbers.add(topBarber);
    }

    return famousBarbers;
}

@Override
public List<BarberDetailResponse> getBySearchName(String name) {
	
	List<Barber> barbers= barberRepository.findByNameToDatabase(name);
	List<BarberDetailResponse> barberDetails= new ArrayList<>();

	for (Barber barber : barbers) {
		Long id = barber.getId();
		List<Rating> ratings = ratingRepository.findByBarberId(id);
		List<Comment> comments = commentRepository.findByBarberId(id);

		double avg = 0;
		if (!ratings.isEmpty()) {
			for (Rating rating : ratings) {
				avg += rating.getRate();
			}
			avg /= ratings.size();
		}

		String fullAddress = barber.getAddressesInfo() != null ? barber.getAddressesInfo().getFullAddress() : "Bilinmeyen Adres";

		BarberDetailResponse barberDetailResponse = BarberDetailResponse.builder()
				.id(id)
				.address(fullAddress)
				.barberName(barber.getBarberName())
				.services(barber.getServices())
				.photoUrl(barber.getPhotoUrl())
				.rate(avg)
				.commentSize(comments.size())
				.build();

		barberDetails.add(barberDetailResponse);
	}

	return barberDetails;
	
}

}
