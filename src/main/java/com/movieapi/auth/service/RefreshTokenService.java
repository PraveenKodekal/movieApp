package com.movieapi.auth.service;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private Logger log= LoggerFactory.getLogger(RefreshTokenService.class);
	
	

	public RefreshTokenService(UserRepository userRepo, RefreshTokenRepository refreshTokenRepo) {
		super();
		this.userRepo = userRepo;
		this.refreshTokenRepo=refreshTokenRepo;
	}



	public RefreshToken createRefreshToken(String userName) {
		
		log.info("createRefreshToken function() started", RefreshTokenService.class);
		User user=userRepo.findByUserEmail(userName)
				.orElseThrow(() ->new UserNotFoundException("User Not Registered with this email : "));
		log.info("RefreshToken User Found ", RefreshTokenService.class);
		RefreshToken refreshToken= user.getRefreshToken();
		
		if(refreshToken == null) {
			log.info("RefreshToken Token  Expiration Toime ",RefreshTokenService.class);

			long refreshTokenValidity= 30*1000;
			refreshToken=RefreshToken.builder()
					.refreshToken(UUID.randomUUID().toString())
					.expirationTime(Instant.now().plusMillis(refreshTokenValidity))
					.user(user)
					.build();
			
			refreshTokenRepo.save(refreshToken);
		
		}
				log.info("create RefreshToken function started" ,RefreshTokenService.class);

		return refreshToken;
	}
	
	
	public RefreshToken verifyRefreshToken(String refreshToken) {
		log.info("VerifyRefreshToken function() started", RefreshTokenService.class);
		RefreshToken refToken=refreshTokenRepo.findByRefreshToken(refreshToken)
									.orElseThrow(()-> new RefreshTokenNotFoundException("RefreshToken Not Found : "));
		if(refToken.getExpirationTime().compareTo(Instant.now())<0) {
			refreshTokenRepo.delete(refToken);
			throw new RefreshTokenExpiredException("RefreshToken Expired");
		}
		log.info("VerifyRefreshToken function() ended ", RefreshTokenService.class);

		return refToken;
	
	}
}
