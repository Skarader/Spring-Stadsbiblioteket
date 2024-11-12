package com.example.Gruppuppgift_Statsbibloteket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admins {
    @Id
    private int admin_id;
    private String username;
    private String password;
    private String role;

    public Admins() {}
    public Admins(int admin_id, String username, String password, String role) {
        this.admin_id = admin_id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
