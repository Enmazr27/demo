package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{user_save_fail}")
    private String name;
    @NotBlank(message = "User Name not null")
    private String username;
    @NotBlank(message = "Password not null")
    private String password;
    @NotBlank(message = "Email not null")
    @Email(message = "Email should be valid")
    private String email;

    public User(Long id, String name, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {
    }
}
