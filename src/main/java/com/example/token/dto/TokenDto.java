package com.example.token.dto;

public class TokenDto {

    private String token;
    private String type;

    public TokenDto(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return this.token;
    }

    public String getType() {
        return this.type;
    }

}
