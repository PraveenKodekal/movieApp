package com.movieapi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.movieapi.dto.MovieDto;

public interface MovieService {
	
	MovieDto addMovie(MovieDto movieDto, MultipartFile file);
	
	MovieDto getMovie(Integer movieId);
	
	List<MovieDto> listOfMovies();

}
