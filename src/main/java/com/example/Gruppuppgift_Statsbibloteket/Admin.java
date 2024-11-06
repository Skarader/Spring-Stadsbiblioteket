package com.example.Gruppuppgift_Statsbibloteket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin {
    private String firstName;
    private String lastName;

    public Admin(String name) {
        this.name = name;
    }
}
