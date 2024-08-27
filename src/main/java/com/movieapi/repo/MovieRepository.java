package com.movieapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieapi.entity.Movies;

@Repository
public interface MovieRepository extends JpaRepository<Movies, Integer> {

}
