package com.example.Gruppuppgift_Statsbibloteket.repository;

import com.example.Gruppuppgift_Statsbibloteket.model.Admins;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminsRepository extends JpaRepository<Admins, Integer> {
    Optional<Admins> findByUsernameAndPassword(String username, String password);
}
