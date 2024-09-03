package com.movieapi.auth.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder

public class ForgotPassword {

	private Integer fId;
	
	private Integer otp;
	
	private Date expirationTime;
	
	private User user;
}
