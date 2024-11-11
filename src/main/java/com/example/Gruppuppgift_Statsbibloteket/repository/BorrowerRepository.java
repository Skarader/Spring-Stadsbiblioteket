package com.example.Gruppuppgift_Statsbibloteket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gruppuppgift_Statsbibloteket.model.Borrower;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
}