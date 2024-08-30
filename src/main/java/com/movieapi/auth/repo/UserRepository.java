package com.movieapi.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieapi.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{


	Optional<User> findByUserEmail(String userName);
}
