package com.example.Gruppuppgift_Statsbibloteket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String member_number;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans;

    public Users() {
    }

    public Users(String first_name, String last_name, String email, String member_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.member_number = member_number;
    }
}
