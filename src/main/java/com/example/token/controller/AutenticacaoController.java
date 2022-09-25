package com.example.token.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.token.dto.LoginDto;
import com.example.token.dto.TokenDto;
import com.example.token.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginDto form) {
        
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();
        
        try {
            String token = tokenService.gerarToken(authManager.authenticate(dadosLogin));
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validarAutenticacao(Principal principal) {
        String usuario = principal.getName();
        return ResponseEntity.ok("{\"email\": \"" + usuario + "\", \"status\": \"autenticado\"}");
    }

}
