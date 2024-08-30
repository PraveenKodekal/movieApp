package com.movieapi.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieapi.auth.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

	
	Optional<RefreshToken> findByRefreshToken(String refreshToken);}
