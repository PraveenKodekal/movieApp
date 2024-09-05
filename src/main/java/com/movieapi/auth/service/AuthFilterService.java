package com.movieapi.auth.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthFilterService extends OncePerRequestFilter {

	private final JwtService jwtService;

	private final UserDetailsService userDetailsService;
	
	private Logger logger= LoggerFactory.getLogger(AuthFilterService.class);

	public AuthFilterService(JwtService jwtService, UserDetailsService userDetailsService) {
		super();
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	 @Override
	    protected void doFilterInternal(@NonNull HttpServletRequest request,
	                                    @NonNull HttpServletResponse response,
	                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

		 logger.info("doFilterInternal function () started", AuthFilterService.class);
	        final String authHeader = request.getHeader("Authorization");
	        String jwt;
	        String username;

	        
	        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	            filterChain.doFilter(request, response);
	            return;
	        }

	        // extract JWT
	        jwt = authHeader.substring(7);

	        // extract username from JWT
	        username = jwtService.extractUsername(jwt);

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	            if(jwtService.isTokenValid(jwt, userDetails)) {
	                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
	                  userDetails,
	                  null,
	                  userDetails.getAuthorities()
	                );

	                authenticationToken.setDetails(
	                        new WebAuthenticationDetailsSource().buildDetails(request)
	                );

	                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	            }
	        }
	        logger.info("doFilterInternal Funtion() ended "+ AuthFilterService.class);

	        filterChain.doFilter(request, response);
	    }

}
