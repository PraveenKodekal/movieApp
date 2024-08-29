package com.movieapi.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.movieapi.dto.MovieDto;
import com.movieapi.dto.MoviePageResponse;

public interface MovieService {
	
	MovieDto addMovie(MovieDto movieDto, MultipartFile file)throws IOException;
	
	MovieDto getMovie(Integer movieId);
	
	List<MovieDto> listOfMovies();
	
	MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file  )throws IOException;

	String deleteMovies(Integer movieId) throws IOException;
	
	MoviePageResponse getAllMoviePagination(Integer pageNumber, Integer pageSize);
	
	MoviePageResponse getAllMoviePaginationAndsorting(Integer pageNumber, Integer pageSize, 
														String sortBy, String dir);

	
}
