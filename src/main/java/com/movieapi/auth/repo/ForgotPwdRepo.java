package com.movieapi.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.movieapi.auth.entity.ForgotPassword;
import com.movieapi.auth.entity.User;

public interface ForgotPwdRepo extends JpaRepository<ForgotPassword, Integer>{

	@Query("select fp from ForgotPassword fp where fp.otp =?1 and fp.user=?2")
	Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);   
	
	
}
