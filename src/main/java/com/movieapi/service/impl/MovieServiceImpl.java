package com.movieapi.service.impl;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movieapi.dto.MovieDto;
import com.movieapi.dto.MoviePageResponse;
import com.movieapi.entity.Movies;
import com.movieapi.exception.FileAlreadyExistsException;
import com.movieapi.exception.MovieNotFoundException;
import com.movieapi.repo.MovieRepository;
import com.movieapi.service.FileService;
import com.movieapi.service.MovieService;
@Service
public class MovieServiceImpl implements MovieService {
	
	private final MovieRepository movieRepo;
	
	
	private final FileService fileService;
	
	@Value("${project.poster}")
	private String path;
	
	@Value("${base.url}")
	private String baseUrl;
	
	private Logger log=LoggerFactory.getLogger(MovieServiceImpl.class);
	
	
	

	public MovieServiceImpl(MovieRepository movieRepo, FileService fileService) {
		super();
		this.movieRepo = movieRepo;
		this.fileService = fileService;
	}
	
	

	@Override
	public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
		log.info("addMovie Service Function() ", MovieServiceImpl.class);		 // 1.upload the file
		if(Files.exists(Paths.get(path+File.separator+file.getOriginalFilename()))) {
			throw new FileAlreadyExistsException("File Already Exists!, please upload another file");
		}
		String uplodedFile=fileService.uploadFile(path, file);
		
		
		// 2. set the value for poster
		movieDto.setPoster(uplodedFile);
		
		//3. map dto to movie entity
		Movies movie= new Movies(
				null, 
				movieDto.getMovieName(), 
				movieDto.getDirector(), 
				movieDto.getProductionHouse(), 
				movieDto.getMovieCasting(),
				movieDto.getReleaseYear(), 
				movieDto.getPoster());
		
		//4 save the movie object
		Movies savedMovie=movieRepo.save(movie);
		
		//5.generate the posterUrl
		String posterUrl=baseUrl+"/file/"+ uplodedFile;
		
		
		//6.map the movie object to dto object
		 MovieDto response= new MovieDto(
		 savedMovie.getMovieId(),
		 savedMovie.getMovieName(),
		 savedMovie.getDirector(),
		 savedMovie.getProductionHouse(),
		 savedMovie.getMovieCasting(),
		 savedMovie.getReleaseYear(),
		 savedMovie.getPoster(), 
		 posterUrl
		 );
		return response;
	}

	@Override
	public MovieDto getMovie(Integer movieId) {

		// check teh data in db exists or not for given Id
		Movies movie= movieRepo.findById(movieId).orElseThrow(()-> new MovieNotFoundException("Movie Not Found with id : "+ movieId));
		
		// 2.generate posterUrl
		String posterUrl=baseUrl+"/file/"+ movie.getPoster();

		
		//3. map to moviedto
		 MovieDto response= new MovieDto(
				 movie.getMovieId(),
				 movie.getMovieName(),
				 movie.getDirector(),
				 movie.getProductionHouse(),
				 movie.getMovieCasting(),
				 movie.getReleaseYear(),
				 movie.getPoster(), 
				 posterUrl
				 );
		
		return response;
	}

	@Override
	public List<MovieDto> listOfMovies() {
		log.info("All Movie List Service Function()", MovieServiceImpl.class);

		// fetch all data from db
		List<Movies> movies=movieRepo.findAll();
		List<MovieDto> movieDtos= new ArrayList<>();
		
		// iterate through list generate posturlforeach movie object and map to dtp
		for(Movies movie: movies) {
			String posterUrl=baseUrl+"/file/"+ movie.getPoster();
			 MovieDto movieDto= new MovieDto(
					 movie.getMovieId(),
					 movie.getMovieName(),
					 movie.getDirector(),
					 movie.getProductionHouse(),
					 movie.getMovieCasting(),
					 movie.getReleaseYear(),
					 movie.getPoster(), 
					 posterUrl
					 );
			 movieDtos.add(movieDto);

		}
		return movieDtos;
	}



	@Override
	public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
		// check movie object exists with given Id
		log.info("updateMovie  Service Function()", MovieServiceImpl.class);

		Movies movie= movieRepo.findById(movieId)
				.orElseThrow(()-> new MovieNotFoundException("Movie Not Found with id : "+ movieId));

		
		// if object is null,  do nothing
		// if object exists, then delete existsing file associated with the record
		// and upload the file
		String fileName=movie.getPoster();
		if(file!=null) {
			Files.deleteIfExists(Paths.get(path+File.separator+fileName));
			fileName=fileService.uploadFile(path, file);
		}
		
		// set movieDtos poster value, accoarding to second step

		movieDto.setPoster(fileName);
		
		
		
		
		// save the movie object ->return thr saved movie object
		Movies movies= new Movies(
				movie.getMovieId(), 
				movieDto.getMovieName(), 
				movieDto.getDirector(), 
				movieDto.getProductionHouse(), 
				movieDto.getMovieCasting(),
				movieDto.getReleaseYear(), 
				movieDto.getPoster());
		Movies updateMovie=movieRepo.save(movies);
		
		
		// generate posterUrl for it
		String posterUrl=baseUrl+"/file/"+fileName;
		
		
		// map to MovieDto and return it
		
		
		 MovieDto response= new MovieDto(
				 movies.getMovieId(),
				 movies.getMovieName(),
				 movies.getDirector(),
				 movies.getProductionHouse(),
				 movies.getMovieCasting(),
				 movies.getReleaseYear(),
				 movies.getPoster(), 
				 posterUrl
				 );
		return response;
	}



	@Override
	public String deleteMovies(Integer movieId) throws IOException {


		log.info("deleteMovie Service Function()", MovieServiceImpl.class);


		// if exists in db
		Movies movie= movieRepo.findById(movieId).orElseThrow(()-> new MovieNotFoundException("Movie Not Found with id : "+ movieId));
		Integer id=movie.getMovieId();
		
		// delete the file if exists
		Files.deleteIfExists(Paths.get(path+File.separator+movie.getPoster()));
		
		// delete the movie object
		movieRepo.delete(movie);
		
		
		return "Movie Deleted with id " +id;
	}



	@Override
	public MoviePageResponse getAllMoviePagination(Integer pageNumber, Integer pageSize) {
		
		log.info("getMoviesWithPagination Service Function()", MovieServiceImpl.class);


		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		
		Page<Movies> moviePages=movieRepo.findAll(pageable);
		
		List<Movies> movies=moviePages.getContent();
		
		List<MovieDto> movieDtos=new ArrayList<>();
		
		for(Movies movie: movies) {
			String posterUrl=baseUrl+"/file/"+ movie.getPoster();
			 MovieDto movieDto= new MovieDto(
					 movie.getMovieId(),
					 movie.getMovieName(),
					 movie.getDirector(),
					 movie.getProductionHouse(),
					 movie.getMovieCasting(),
					 movie.getReleaseYear(),
					 movie.getPoster(), 
					 posterUrl
					 );
			 movieDtos.add(movieDto);
		
		}
		
		return new MoviePageResponse(movieDtos, pageNumber, pageSize, 
											moviePages.getTotalElements(), 
											moviePages.getTotalPages(), 
											moviePages.isLast());
	}



	@Override
	public MoviePageResponse getAllMoviePaginationAndsorting(Integer pageNumber, Integer pageSize, String sortBy,
			String dir) {
		
		log.info("getMoviewithPagination And Sorting Service Function()", MovieServiceImpl.class);

		
		Sort sort= dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
													:Sort.by(sortBy).descending();
		
		
		 
		
Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Movies> moviePages=movieRepo.findAll(pageable);
		
		List<Movies> movies=moviePages.getContent();
		
		List<MovieDto> movieDtos=new ArrayList<>();
		
		for(Movies movie: movies) {
			String posterUrl=baseUrl+"/file/"+ movie.getPoster();
			 MovieDto movieDto= new MovieDto(
					 movie.getMovieId(),
					 movie.getMovieName(),
					 movie.getDirector(),
					 movie.getProductionHouse(),
					 movie.getMovieCasting(),
					 movie.getReleaseYear(),
					 movie.getPoster(), 
					 posterUrl
					 );
			 movieDtos.add(movieDto);
		
		}
		
		return new MoviePageResponse(movieDtos, pageNumber, pageSize, 
											moviePages.getTotalElements(), 
											moviePages.getTotalPages(), 
											moviePages.isLast());
	}

}
