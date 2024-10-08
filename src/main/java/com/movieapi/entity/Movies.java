package com.movieapi.entity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Movies {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer movieId;
	
	@Column(nullable = false)
	@NotBlank(message = "Please fill appropriate field")
	private String movieName;
	
	@Column(nullable = false)
	@NotBlank(message = "Please fill appropriate field")
	private String productionHouse;
	
	@Column(nullable = false)
	@NotBlank(message = "Please fill appropriate field")
	private String director;
	
	
	@ElementCollection
	@CollectionTable(name="movieCast")
	private Set<String> movieCasting;
	
	@Column(nullable = false)
	private Integer releaseYear;
	
	@Column(nullable = false)
	@NotBlank(message = "Please fill appropriate field")
	private String poster;
	
	
	public Movies() {}
	
	
	public Movies(Integer movieId, String movieName, String productionHouse, String director, Set<String> movieCasting,
			Integer releaseYear, String poster) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.productionHouse = productionHouse;
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
