package com.example.Gruppuppgift_Statsbibloteket.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
// @Setter
// @Getter
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @NotBlank(message = "Firstname cannot be blank")
    @Column(name = "first_name")
    private String name;

    @NotBlank(message = "Lastname cannot be blank")
    @Column(name = "last_name")
    private String last_name;

    @NotBlank(message = "Email cannot be blank")
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "member_number", unique = true)
    private String member_number;

    @NotBlank(message = "username cannot be blank")
    @Size(min = 6, max = 30, message = "usernmae must be atleast 6 chars and max 30 chars")
    @Pattern(regexp = "^(?=.*[A-Z])[A-Za-z]+$", message = "Username must include one uppercase letter, username can only include letters")
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 6, message = "Password must be atleast 6 chars long")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans;

    public Users() {
    }

    public Users(String name, String last_name, String email, String member_number) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.member_number = member_number;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMember_number() {
        return member_number;
    }

    public void setMember_number(String member_number) {
        this.member_number = member_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
