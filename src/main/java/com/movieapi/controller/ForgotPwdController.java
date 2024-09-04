package com.movieapi.controller;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieapi.auth.entity.ForgotPassword;
import com.movieapi.auth.entity.User;
import com.movieapi.auth.repo.ForgotPwdRepo;
import com.movieapi.auth.repo.UserRepository;
import com.movieapi.dto.MailBody;
import com.movieapi.exception.OtpIsNotValidException;
import com.movieapi.exception.UserNotFoundException;
import com.movieapi.service.EmailServices;
import com.movieapi.utils.ChangePassword;

@RestController
@RequestMapping("/forgotPwd")
public class ForgotPwdController {

	private UserRepository userRepo;

	private EmailServices emailService;

	private ForgotPwdRepo forgotPwdRepo;
	
	private final PasswordEncoder pwdEncoder;
	
	

	public ForgotPwdController(UserRepository userRepo, EmailServices emailService, ForgotPwdRepo forgotPwdRepo,
			PasswordEncoder pwdEncoder) {
		super();
		this.userRepo = userRepo;
		this.emailService = emailService;
		this.forgotPwdRepo = forgotPwdRepo;
		this.pwdEncoder = pwdEncoder;
	}

	// send mail for mail verification

	@PostMapping("/verifyEmail/{email}")
	public ResponseEntity<String> verifyEmail(@PathVariable String email) {

		User user = userRepo.findByUserEmail(email).orElseThrow(() -> new UserNotFoundException(email));

		int otp = otpGenerator();
		MailBody mailBody = MailBody.builder().to(email).text("This is OTP for forgot Pwd "+otp)
				.subject("OTP for forgot pwd request").build();

		ForgotPassword fp = ForgotPassword.builder().otp(otp)
				.expirationTime(new Date(System.currentTimeMillis() + 90 * 1000)).user(user).build();

		emailService.sendSimpleMessage(mailBody);

		forgotPwdRepo.save(fp);

		return ResponseEntity.ok("Send Email For Verification");

	}

	// Enter OTP
	@PostMapping("/verifyOtp/{otp}/{email}")
	public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {

		User user = userRepo.findByUserEmail(email).orElseThrow(() -> new UserNotFoundException(email));

		// fetch given User email and otp present in DB or not
		ForgotPassword fp = forgotPwdRepo.findByOtpAndUser(otp, user)
				.orElseThrow(() -> new OtpIsNotValidException("otp is not valid"));

		//check valid user for valid otp
		
		// this otp is still valid or not
		if(fp.getExpirationTime().before(Date.from(Instant.now()))) {
			forgotPwdRepo.deleteById(fp.getFpId());
			return  new ResponseEntity<String>("OTP has been Expired " , HttpStatus.EXPECTATION_FAILED);

		}
		return ResponseEntity.ok("OTP Verified");
		
	}
	
	
	//setUp new Pwd
	@PostMapping("/resetPwd/{emial}")
	public ResponseEntity<String> changePwdHandler(@RequestBody ChangePassword changePwd,
			@PathVariable String email){
		// password entered from the user and reenter password should be same
		if(!Objects.equals(changePwd.password(), changePwd.repeatPassword())) {
			return new ResponseEntity<>("Please enter the pwd again", HttpStatus.EXPECTATION_FAILED);
		}
		
//		if entered and reentered pwds are not same
		// Encode and Decode the pwds
		
		String encodedPwd= pwdEncoder.encode(changePwd.password());
		userRepo.updatePassword(email, encodedPwd);
		return  ResponseEntity.ok("Pssword has been updated Successfully");
	}
	
	

	private Integer otpGenerator() {
		Random random = new Random();
		return random.nextInt(100000, 999999);
	}

}
