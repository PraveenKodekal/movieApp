package com.movieapi.dto;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;

public class MovieDto {
	
	private Integer movieId;
	
	@Column(nullable = false)
	@NotBlank(message = "Please fill appropriate field")
	private String MovieName;
	@Column(nullable = false)
	@NotBlank(message = "Please fill appropriate field")
	private String ProductionHouse;
	@Column(nullable = false)
	@NotBlank(message = "Please fill appropriate field")
	private String director;
	
	
	
	private Set<String> movieCasting;
	
	@Column(nullable = false)
	
	private Integer releaseYear;
	
	@Column(nullable = false)
	@NotBlank(message = "Please fill appropriate field")
	private String poster;
	
	
	private String pisterUrl;
	
	
	public MovieDto(Integer movieId, String movieName, String productionHouse, String director, Set<String> movieCasting,
			Integer releaseYear, String poster) {
		super();
		this.movieId = movieId;
		MovieName = movieName;
		ProductionHouse = productionHouse;
		this.director = director;
		this.movieCasting = movieCasting;
		this.releaseYear = releaseYear;
		this.poster = poster;
	}


	public Integer getMovieId() {
		return movieId;
	}


	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}


	public String getMovieName() {
		return MovieName;
	}


	public void setMovieName(String movieName) {
		MovieName = movieName;
	}


	public String getProductionHouse() {
		return ProductionHouse;
	}


	public void setProductionHouse(String productionHouse) {
		ProductionHouse = productionHouse;
	}


	public String getDirector() {
		return director;
	}


	public void setDirector(String director) {
		this.director = director;
	}


	public Set<String> getMovieCasting() {
		return movieCasting;
	}


	public void setMovieCasting(Set<String> movieCasting) {
		this.movieCasting = movieCasting;
	}


	public Integer getReleaseYear() {
		return releaseYear;
	}


	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}


	public String getPoster() {
		return poster;
	}


	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	
	
	

}
