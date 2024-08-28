package com.movieapi.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.movieapi.dto.MovieDto;

public interface MovieService {
	
	MovieDto addMovie(MovieDto movieDto, MultipartFile file)throws IOException;
	
	MovieDto getMovie(Integer movieId);
	
	List<MovieDto> listOfMovies();
	
	MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file  )throws IOException;

	String deleteMovies(Integer movieId) throws IOException;
}
