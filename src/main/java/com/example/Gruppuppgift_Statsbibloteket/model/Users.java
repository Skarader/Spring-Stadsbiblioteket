package com.example.Gruppuppgift_Statsbibloteket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String member_number;
    public Users() {}

    public Users(String first_name, String last_name, String email, String member_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.member_number = member_number;
    }
}
