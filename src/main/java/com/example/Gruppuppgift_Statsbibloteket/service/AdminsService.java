package com.example.Gruppuppgift_Statsbibloteket.service;

import com.example.Gruppuppgift_Statsbibloteket.model.Admins;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.repository.AdminsRepository;
import com.example.Gruppuppgift_Statsbibloteket.service.BookService;
import com.example.Gruppuppgift_Statsbibloteket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminsService {
    private final AdminsRepository adminsRepository;
    private final UserRepository userRepository;
    private final BookService bookService;
    public AdminsService(AdminsRepository adminsRepository, UserRepository userRepository, BookService bookService) {
        this.adminsRepository = adminsRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
    }

    public List<Admins> getAllAdmins() {
        return adminsRepository.findAll();
    }

    public Users createUser(Users newUser, String username, String password) {
        Optional<Admins> admin = adminsRepository.findByUsernameAndPassword(username, password);
        if (admin.isPresent()) {
            return userRepository.save(newUser);
        }
        else {
           throw new SecurityException("Test");
        }
    }

    public void createBook(Book newBook, String username, String password) {
        Optional<Admins> admin = adminsRepository.findByUsernameAndPassword(username, password);
        if (admin.isPresent()) {
            bookService.saveBook(newBook);
        }
        else {
            throw new SecurityException("Test");
        }
    }


}
