package com.movieapi.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movieapi.dto.MovieDto;
import com.movieapi.entity.Movies;
import com.movieapi.repo.MovieRepository;
import com.movieapi.service.FileService;
import com.movieapi.service.MovieService;
@Service
public class MovieServiceImpl implements MovieService {
	@Autowired
	private final MovieRepository movieRepo;
	
	@Autowired
	private final FileService fileService;
	
	@Value("${project.poster}")
	private String path;
	
	@Value("${base.url}")
	private String baseUrl;
	
	
	

	public MovieServiceImpl(MovieRepository movieRepo, FileService fileService) {
		super();
		this.movieRepo = movieRepo;
		this.fileService = fileService;
	}
	
	

	@Override
	public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
		 // 1.upload the file
		String uplodedFile=fileService.uploadFile(path, file);
		
		
		// 2. set the value for poster
		movieDto.setPoster(uplodedFile);
		
		//3. map dto to movie entity
		Movies movie= new Movies(
				movieDto.getMovieId(), 
				movieDto.getMovieName(), 
				movieDto.getDirector(), 
				movieDto.getProductionHouse(), 
				movieDto.getMovieCasting(),
				movieDto.getReleaseYear(), 
				movieDto.getPoster());
		
		//4 save the movie object
		Movies savedMovie=movieRepo.save(movie);
		
		//5.generate the posterUrl
		String posterUrl=baseUrl+"/file"+ uplodedFile;
		
		
		//6.map the movie object to dto object
		 MovieDto response= new MovieDto(
		 savedMovie.getMovieId(),
		 savedMovie.getMovieName(),
		 savedMovie.getDirector(),
		 savedMovie.getProductionHouse(),
		 savedMovie.getMovieCasting(),
		 savedMovie.getReleaseYear(),
		 savedMovie.getPoster(),posterUrl
		 );
		return response;
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
