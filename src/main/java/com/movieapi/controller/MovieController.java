package com.movieapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapi.dto.MovieDto;
import com.movieapi.dto.MoviePageResponse;
import com.movieapi.exception.EmptyFileException;
import com.movieapi.exception.FileAlreadyExistsException;
import com.movieapi.service.impl.MovieServiceImpl;
import com.movieapi.utils.AppConstants;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
	
	
	private final MovieServiceImpl movieService;

	public MovieController(MovieServiceImpl movieService) {
		super();
		this.movieService = movieService;
	}
	
	@PostMapping("/addMovie")
	public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,
													@RequestPart String movieDto) throws IOException, EmptyFileException{
		
		if(file.isEmpty()) {
			throw new EmptyFileException("File is Empty, upload new file");
		}
		
		// convert String to json
		MovieDto dto= convertToMovieDto(movieDto);
		return new ResponseEntity<>(movieService.addMovie(dto, file),HttpStatus.CREATED);
		
	}
	
	
	@GetMapping("/{movieId}")
	public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId){
		
		return new ResponseEntity<>(movieService.getMovie(movieId), HttpStatus.OK);
	}
	

	@GetMapping("/movieList")
	public ResponseEntity<List<MovieDto>> getAllMovieHandler(){
		return new ResponseEntity<>(movieService.listOfMovies(), HttpStatus.OK);
	}
	
	
	@PutMapping("/update/{movieId}")
	public ResponseEntity<MovieDto> getUpdateHandler(@PathVariable Integer movieId,
													@RequestPart MultipartFile file,
													@RequestPart String movieDtoObj) throws IOException{
		if(file.isEmpty())	file=null;
		
		
		
		MovieDto movieDto=convertToMovieDto(movieDtoObj);
		return ResponseEntity.ok(movieService.updateMovie(movieId, movieDto, file));
	}
	
	
	@DeleteMapping("/delete/{movieId}")
	public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException{
		
		
		return ResponseEntity.ok(movieService.deleteMovies(movieId)) ;
	}
	
	@GetMapping("/allMoviePage")
	public ResponseEntity<MoviePageResponse> getMoviesPagination(@RequestParam(defaultValue=AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
																@RequestParam(defaultValue=AppConstants.PAGE_SIZE, required=false) Integer pageSize) {
		
		
		
		return ResponseEntity.ok(movieService.getAllMoviePagination(pageNumber, pageSize));
	}
	
	@GetMapping("/allMoviePagewithSort")
	public ResponseEntity<MoviePageResponse> getMoviesPaginationAndSorting(@RequestParam(defaultValue=AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
																@RequestParam(defaultValue=AppConstants.PAGE_SIZE, required=false) Integer pageSize,
																@RequestParam(defaultValue=AppConstants.SORT_BY) String sortBy,
																@RequestParam(defaultValue=AppConstants.SORT_DIR) String dir) {
		
		
		
		return ResponseEntity.ok(movieService.getAllMoviePaginationAndsorting(pageNumber, pageSize,sortBy, dir));
	}
	
	
	
	

	//To convert dto object as json object
	private MovieDto convertToMovieDto(String MovieDtoToObj) throws JsonMappingException, JsonProcessingException {
		//MovieDto movieDto=new MovieDto();
		ObjectMapper objectMapper= new ObjectMapper();
		return objectMapper.readValue(MovieDtoToObj, MovieDto.class);
		
	}
	
}
