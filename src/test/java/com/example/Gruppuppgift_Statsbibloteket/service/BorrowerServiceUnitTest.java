package com.example.Gruppuppgift_Statsbibloteket.service;

import com.example.Gruppuppgift_Statsbibloteket.model.Borrower;
import com.example.Gruppuppgift_Statsbibloteket.repository.BorrowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;  // Lägg till denna import

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BorrowerServiceUnitTest {

    @Mock
    private BorrowerRepository borrowerRepository;  // Mocka BorrowerRepository

    @InjectMocks
    private BorrowerService borrowerService; // Den service vi testar

    private Borrower borrower;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialisera mocks
        borrower = new Borrower("Test User", "testuser@example.com", Set.of());  // Skapa en ny låntagare
    }

    @Test
    public void testSaveBorrower() {
        // När save() kallas på borrowerRepository, returnera den låntagare vi skapar här
        when(borrowerRepository.save(borrower)).thenReturn(borrower);

        // Anropa saveBorrower i vår service
        Borrower savedBorrower = borrowerService.saveBorrower(borrower);

        // Verifiera att save() kallas på repository med rätt objekt
        verify(borrowerRepository, times(1)).save(borrower);

        // Verifiera att vi fått rätt data tillbaka från service
        assertThat(savedBorrower).isNotNull();
        assertThat(savedBorrower.getName()).isEqualTo("Test User");
        assertThat(savedBorrower.getEmail()).isEqualTo("testuser@example.com");
    }

    @Test
    public void testGetBorrowerById() {
        // Skapa en låntagare och mocka findById
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));

        // Anropa getBorrowerById i vår service
        Optional<Borrower> foundBorrower = borrowerService.getBorrowerById(1L);

        // Verifiera att findById() kallas på repository
        verify(borrowerRepository, times(1)).findById(1L);

        // Verifiera att rätt låntagare returneras
        assertThat(foundBorrower).isPresent();
        assertThat(foundBorrower.get().getName()).isEqualTo("Test User");
        assertThat(foundBorrower.get().getEmail()).isEqualTo("testuser@example.com");
    }

    @Test
    public void testDeleteBorrower() {
        // Anropa deleteBorrower i vår service
        borrowerService.deleteBorrower(1L);

        // Verifiera att deleteById() kallas på repository
        verify(borrowerRepository, times(1)).deleteById(1L);
    }
}