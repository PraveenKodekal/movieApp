package com.movieapi.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movieapi.auth.entity.User;
import com.movieapi.auth.entity.UserRole;
import com.movieapi.auth.repo.UserRepository;
import com.movieapi.auth.utils.AuthResponse;
import com.movieapi.auth.utils.LoginRequest;
import com.movieapi.auth.utils.RegisterRequest;
import com.movieapi.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;
	private final AuthenticationManager authenticationManager;
	
	private Logger log=LoggerFactory.getLogger(AuthService.class);

	public AuthResponse register(RegisterRequest registerRequest) {
		
		log.info("Register Function() "+ AuthService.class);
		
		var user = User.builder().name(registerRequest.getName()).userEmail(registerRequest.getUserEmail())
				.userName(registerRequest.getUserName()).password(passwordEncoder.encode(registerRequest.getPassword()))
				.role(UserRole.USER).build();

		User savedUser = userRepository.save(user);
		var accessToken = jwtService.generateToken(savedUser);
		var refreshToken = refreshTokenService.createRefreshToken(savedUser.getUserEmail());
		
		log.info("Register Function() ended"+ AuthService.class);

		return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken.getRefreshToken()).build();

	}

	public AuthResponse login(LoginRequest loginRequest) {
		
		log.info("LoginRequest Function() started"+ AuthService.class);

		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(), loginRequest.getPassword()));

		var user1 = userRepository.findByUserEmail(loginRequest.getUserEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
		var accessToken = jwtService.generateToken(user1);
		var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUserEmail());

		log.info("LoginRequest Function() ended"+ AuthService.class);

		return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken.getRefreshToken()).build();
	}
}
