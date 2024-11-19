package com.example.Gruppuppgift_Statsbibloteket.controller;

import com.example.Gruppuppgift_Statsbibloteket.Dto.AdminAddBookDTO;
import com.example.Gruppuppgift_Statsbibloteket.Dto.BookDTO;
import com.example.Gruppuppgift_Statsbibloteket.model.Admins;
import com.example.Gruppuppgift_Statsbibloteket.repository.AdminsRepository;
import com.example.Gruppuppgift_Statsbibloteket.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminsRepository adminsRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        adminsRepository.deleteAll();

        Admins admin = new Admins();
        admin.setRole("ADMIN");
        admin.setUsername("eddie");
        admin.setPassword("abc123");
        adminsRepository.save(admin);
    }

    @Test
    void createBookIfAdminIsPresent() throws Exception {
        AdminAddBookDTO adminAddBookDTO = new AdminAddBookDTO();

        adminAddBookDTO.setNewBook(new BookDTO());
        adminAddBookDTO.setUsername("eddie");
        adminAddBookDTO.setPassword("abc123");

        ResultActions response = mockMvc.perform(post("/admins/create/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "username": "eddie",
                        "password": "abc123",
                          "newBook": {
                            "title": "Test Book",
                            "publicationYear": 2024,
                            "authorId": 1,
                            "available": true,
                            "bookGenreIds": [2]
                          }
                        }
                        """));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Book")).hasJsonPath())
                .andExpect(jsonPath("$.publicationYear", is(2024)).hasJsonPath());
    }

    @Test
    void createBookFailIfAdminIsNotPresent() throws Exception {
        AdminAddBookDTO adminAddBookDTO = new AdminAddBookDTO();

        adminAddBookDTO.setNewBook(new BookDTO());
        adminAddBookDTO.setUsername("eddie2");
        adminAddBookDTO.setPassword("abc1234");

        ResultActions response = mockMvc.perform(post("/admins/create/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "username": "wrong username",
                        "password": "wrong password",
                          "newBook": {
                            "title": "Test Book2",
                            "publicationYear": 2024,
                            "authorId": 2,
                            "available": true,
                            "bookGenreIds": [1]
                          }
                        }
                        """)
                );

                response.andExpect(status().isForbidden());
    }



}
