package com.movieapi.auth.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.movieapi.auth.entity.RefreshToken;
import com.movieapi.auth.entity.User;
import com.movieapi.auth.repo.RefreshTokenRepository;
import com.movieapi.auth.repo.UserRepository;
import com.movieapi.exception.RefreshTokenExpiredException;
import com.movieapi.exception.RefreshTokenNotFoundException;
import com.movieapi.exception.UserNotFoundException;

@Service
public class RefreshTokenService {
	
	private final UserRepository userRepo;
	
	private final RefreshTokenRepository refreshTokenRepo;
	
	

	public RefreshTokenService(UserRepository userRepo, RefreshTokenRepository refreshTokenRepo) {
		super();
		this.userRepo = userRepo;
		this.refreshTokenRepo=refreshTokenRepo;
	}



	public RefreshToken createRefreshToken(String userName) {
		User user=userRepo.findByUserEmail(userName)
				.orElseThrow(() ->new UserNotFoundException("User Not Registered with this email : "));
		
		RefreshToken refreshToken= user.getRefreshToken();
		
		if(refreshToken == null) {
			long refreshTokenValidity= 30*1000;
			refreshToken=RefreshToken.builder()
					.refreshToken(UUID.randomUUID().toString())
					.expirationTime(Instant.now().plusMillis(refreshTokenValidity))
					.user(user)
					.build();
			
			refreshTokenRepo.save(refreshToken);
		
		}
		return refreshToken;
	}
	
	
	public RefreshToken verifyRefreshToken(String refreshToken) {
		RefreshToken refToken=refreshTokenRepo.findByRefreshToken(refreshToken)
									.orElseThrow(()-> new RefreshTokenNotFoundException("RefreshToken Not Found : "));
		if(refToken.getExpirationTime().compareTo(Instant.now())<0) {
			refreshTokenRepo.delete(refToken);
			throw new RefreshTokenExpiredException("RefreshToken Expired");
		}
		
		return refToken;
	
	}
}
