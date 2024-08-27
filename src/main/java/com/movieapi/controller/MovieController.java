package com.movieapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapi.dto.MovieDto;
import com.movieapi.service.impl.MovieServiceImpl;

@RestController("/api/v1/movie")
public class MovieController {
	
	@Autowired
	private final MovieServiceImpl movieService;

	public MovieController(MovieServiceImpl movieService) {
		super();
		this.movieService = movieService;
	}
	
	@PostMapping("/addMovie")
	public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,
													@RequestPart String movieDto) throws IOException{
		// convert String to json
		MovieDto dto= convertToMovieDto(movieDto);
		return new ResponseEntity<>(movieService.addMovie(dto, file),HttpStatus.CREATED);
		
	}

	//To convert dto object as json object
	private MovieDto convertToMovieDto(String MovieDtoToObj) throws JsonMappingException, JsonProcessingException {
		//MovieDto movieDto=new MovieDto();
		ObjectMapper objectMapper= new ObjectMapper();
		return objectMapper.readValue(MovieDtoToObj, MovieDto.class);
		
	}
	
}
