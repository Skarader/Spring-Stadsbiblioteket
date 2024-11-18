package com.example.Gruppuppgift_Statsbibloteket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="admins")
public class Admins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int admin_id;
    private String username;
    private String password;
    private String role;

    public Admins() {}
    public Admins(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
