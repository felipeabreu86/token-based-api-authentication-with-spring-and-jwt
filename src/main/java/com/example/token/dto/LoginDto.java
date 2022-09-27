package com.example.token.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDto {

    private String email;

    @JsonProperty("password")
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(this.email, this.senha);
    }

}