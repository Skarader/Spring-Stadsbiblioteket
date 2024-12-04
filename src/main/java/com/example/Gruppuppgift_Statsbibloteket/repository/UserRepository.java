package com.example.Gruppuppgift_Statsbibloteket.repository;

import com.example.Gruppuppgift_Statsbibloteket.model.Users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> { // Changed Integer to Long

    Optional<Users> findByName(String username);

}
