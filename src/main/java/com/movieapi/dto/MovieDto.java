package com.movieapi.dto;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;

public class MovieDto {
	
	private Integer movieId;
	
	@NotBlank(message = "Please fill appropriate field")
	private String movieName;
	
	
	@NotBlank(message = "Please fill appropriate field")
	private String productionHouse;
	
	
	@NotBlank(message = "Please fill appropriate field")
	private String director;
	
	
	
	private Set<String> movieCasting;
	
	private Integer releaseYear;
	
	@NotBlank(message = "Please fill appropriate field")
	private String poster;
	
	@NotBlank(message = "Please fill appropriate field")
	private String posterUrl;
	
	
	public MovieDto(Integer movieId, String movieName, String productionHouse, String director, Set<String> movieCasting,
			Integer releaseYear, String poster, String posterUrl) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.productionHouse = productionHouse;
		this.director = director;
		this.movieCasting = movieCasting;
		this.releaseYear = releaseYear;
		this.poster = poster;
		this.posterUrl=posterUrl;
	}


	public String getPosterUrl() {
		return posterUrl;
	}


	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}


	public MovieDto() {
		// TODO Auto-generated constructor stub
	}


	public Integer getMovieId() {
		return movieId;
	}


	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}


	public String getMovieName() {
		return movieName;
	}


	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}


	public String getProductionHouse() {
		return productionHouse;
	}


	public void setProductionHouse(String productionHouse) {
		this.productionHouse = productionHouse;
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
