package com.movieapi.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.movieapi.auth.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer>{


	Optional<User> findByUserEmail(String userName);
	//This only query doesnot update it will gives an error
//	we can use @Modifying and @Transactional it specifies that something is going to modify and delete
	@Modifying
	@Transactional
	@Query("update User u set u.password = ?2 where u.userEmail =?1")
	void updatePassword(String email, String password);
}
