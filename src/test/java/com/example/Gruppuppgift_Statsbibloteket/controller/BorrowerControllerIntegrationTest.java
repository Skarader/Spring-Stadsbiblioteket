package com.example.Gruppuppgift_Statsbibloteket.controller;

import com.example.Gruppuppgift_Statsbibloteket.model.Borrower;
import com.example.Gruppuppgift_Statsbibloteket.repository.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BorrowerControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Test
    public void testCreateAndGetBorrower() {
        // 1. Skapa en ny låntagare
        Borrower newBorrower = new Borrower("Test User", "testuser@example.com", Set.of());
        ResponseEntity<Borrower> createResponse = restTemplate.postForEntity("/api/borrowers", newBorrower, Borrower.class);

        // Kontrollera att POST-anropet lyckades
        assertThat(createResponse.getStatusCodeValue()).isEqualTo(201);

        Borrower createdBorrower = createResponse.getBody();
        assertThat(createdBorrower).isNotNull();
        assertThat(createdBorrower.getName()).isEqualTo("Test User");
        assertThat(createdBorrower.getEmail()).isEqualTo("testuser@example.com");

        // 2. Hämta låntagaren via GET
        ResponseEntity<Borrower> getResponse = restTemplate.getForEntity("/api/borrowers/" + createdBorrower.getBorrowerId(), Borrower.class);

        // Kontrollera att GET-anropet lyckades och att datan är korrekt
        assertThat(getResponse.getStatusCodeValue()).isEqualTo(200);
        Borrower fetchedBorrower = getResponse.getBody();
        assertThat(fetchedBorrower).isNotNull();
        assertThat(fetchedBorrower.getName()).isEqualTo("Test User");
        assertThat(fetchedBorrower.getEmail()).isEqualTo("testuser@example.com");
    }
}