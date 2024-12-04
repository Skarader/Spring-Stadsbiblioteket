package com.example.Gruppuppgift_Statsbibloteket.service;

import com.example.Gruppuppgift_Statsbibloteket.Dto.BookDTO;
import com.example.Gruppuppgift_Statsbibloteket.model.Admins;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.repository.AdminsRepository;
import com.example.Gruppuppgift_Statsbibloteket.repository.BookRepository;
import com.example.Gruppuppgift_Statsbibloteket.service.BookService;
import com.example.Gruppuppgift_Statsbibloteket.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AdminsService {
    private final AdminsRepository adminsRepository;
    private final UserRepository userRepository;
    private final BookService bookService;
    private PasswordEncoder passwordEncoder;

    public AdminsService(AdminsRepository adminsRepository, UserRepository userRepository, BookService bookService,
            PasswordEncoder passwordEncoder) {
        this.adminsRepository = adminsRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Admins> getAllAdmins() {
        return adminsRepository.findAll();
    }

    public Users createUser(Users newUser, String username, String password) {
        Optional<Admins> admin = adminsRepository.findByUsernameAndPassword(username, password);
        if (admin.isPresent()) {
            return userRepository.save(newUser);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }

    public Book createBook(BookDTO bookDTO, String username, String password) {
        Optional<Admins> admin = adminsRepository.findByUsernameAndPassword(username, password);
        if (admin.isPresent()) {
            return bookService.createBook(bookDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }

    public Admins createAdmin(String username, String rawPassword, String role) {
        Admins admin = new Admins();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(rawPassword));
        admin.setRole(role);

        return adminsRepository.save(admin);
    }

    public List<Book> getBorrowedBooks(String username, String password) {
        Optional<Admins> admin = adminsRepository.findByUsernameAndPassword(username, password);
        if (admin.isPresent()) {
            return this.bookService.getBorrowedBooks();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

    }

    public Book updateBookInfo(String username, String password, Long id, BookDTO bookDTO) {
        Optional<Admins> admin = adminsRepository.findByUsernameAndPassword(username, password);
        if (admin.isPresent()) {
            return this.bookService.updateBook(id, bookDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }

    public void deleteAdmins() {
        adminsRepository.deleteAll();
    }

}
