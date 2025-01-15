package com.example.Gruppuppgift_Statsbibloteket.controller;

import com.example.Gruppuppgift_Statsbibloteket.Dto.AdminAddBookDTO;
import com.example.Gruppuppgift_Statsbibloteket.Dto.AdminAddUserDTO;
import com.example.Gruppuppgift_Statsbibloteket.Dto.AdminGetBorrowedBooks;
import com.example.Gruppuppgift_Statsbibloteket.Dto.BookDTO;
import com.example.Gruppuppgift_Statsbibloteket.model.Admins;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.service.AdminsService;
import com.example.Gruppuppgift_Statsbibloteket.service.LoanService;

import jakarta.websocket.server.PathParam;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminsController {
    private final AdminsService adminsService;
    private final LoanService loanService;

    public AdminsController(AdminsService adminsService, LoanService loanService) {
        this.adminsService = adminsService;
        this.loanService = loanService;
    }

    @GetMapping
    public List<Admins> getAllAdmins() {
        return adminsService.getAllAdmins();
    }

    @PostMapping("/create/book")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public Book createBook(@RequestBody AdminAddBookDTO request) {
        return adminsService.createBook(
                request.getNewBook(),
                request.getUsername(),
                request.getPassword());
    }

    @GetMapping("/{username}/{password}/borrowed-books")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public List<Book> getBorrowedBooks(@PathVariable String username, @PathVariable String password) {
        return adminsService.getBorrowedBooks(username, password);
    }

    @PutMapping("/{username}/{password}/update/book/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public Book updateBook(@PathVariable String username, @PathVariable String password, @PathVariable Long id,
            @RequestBody BookDTO bookDTO) {
        return adminsService.updateBookInfo(username, password, id, bookDTO);
    }

    @GetMapping("/get-borrowed-books")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public List<AdminGetBorrowedBooks> getBorrowedBooks() {
        return this.loanService.getBorrowedBooksWithUsers();
    }

}
