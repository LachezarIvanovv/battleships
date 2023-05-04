package com.example.battleships.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginDTO {

    @NotBlank
    @Size(min = 3, max = 10)
    public String username;

    @NotBlank
    @Size(min = 3)
    public String password;

    public LoginDTO(){
    }

    public String getUsername() {
        return username;
    }

    public LoginDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
