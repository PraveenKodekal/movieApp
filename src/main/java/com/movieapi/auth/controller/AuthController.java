package com.movieapi.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieapi.auth.entity.RefreshToken;
import com.movieapi.auth.entity.User;
import com.movieapi.auth.service.AuthService;
import com.movieapi.auth.service.JwtService;
import com.movieapi.auth.service.RefreshTokenService;
import com.movieapi.auth.utils.AuthResponse;
import com.movieapi.auth.utils.LoginRequest;
import com.movieapi.auth.utils.RefreshTokenRequest;
import com.movieapi.auth.utils.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	private final AuthService authService;
	
	private final RefreshTokenService refreshService;
	
	private final JwtService jwtService;
	

	public AuthController(AuthService authService, RefreshTokenService refreshService
			,JwtService jwtService) {
		super();
		this.authService = authService;
		this.refreshService=refreshService;
		this.jwtService=jwtService;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> handleregister(@RequestBody RegisterRequest request){
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> handleLogin(@RequestBody LoginRequest request){
		return ResponseEntity.ok(authService.login(request));
	}
	
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshToken){
		
		RefreshToken token= refreshService.verifyRefreshToken(refreshToken.getRefreshToken());
		
		User user= token.getUser();
		
		String accessToken= jwtService.generateToken(user);
		
		return ResponseEntity.ok(AuthResponse.builder()
								.accessToken(accessToken)
								.refreshToken(token.getRefreshToken())
								.build());
	}
	
}
