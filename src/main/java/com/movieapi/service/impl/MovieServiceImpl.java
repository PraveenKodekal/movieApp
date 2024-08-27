package com.movieapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movieapi.dto.MovieDto;
import com.movieapi.service.MovieService;
@Service
public class MovieServiceImpl implements MovieService {

	@Override
	public MovieDto addMovie(MovieDto movieDto, MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MovieDto getMovie(Integer movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MovieDto> listOfMovies() {
		// TODO Auto-generated method stub
		return null;
	}

}
