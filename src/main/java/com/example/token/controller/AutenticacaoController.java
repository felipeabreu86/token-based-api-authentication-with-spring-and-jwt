package com.example.token.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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

import com.example.token.dto.DadosAutenticacaoDto;
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

    /**
     * Realiza a autenticação do usuário e retorna um JSON informando o Token e o
     * seu tipo (Bearer).
     * 
     * @param loginDto - dados do usuário a ser autenticado.
     * @return instância de TokenDto.
     */
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginDto loginDto) {

        Optional<String> token = Optional.empty();

        try {
            UsernamePasswordAuthenticationToken dadosLogin = loginDto.converter();
            token = tokenService.gerarToken(authManager.authenticate(dadosLogin));
        } catch (Exception e) { }

        return token.isPresent() 
                ? ResponseEntity.ok(new TokenDto(token.get(), "Bearer")) 
                : ResponseEntity.badRequest().build();
    }

    /**
     * Retorna um JSON indicando o usuário autenticado e a data de expiração do
     * Token.
     * 
     * @param request   - requisição HTTP.
     * @param principal - entidade que representa o usuário autenticado.
     * @return instância de DadosAutenticacaoDto.
     */
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DadosAutenticacaoDto> validarAutenticacao(HttpServletRequest request, Principal principal) {

        return ResponseEntity.ok(
                new DadosAutenticacaoDto(principal.getName(), tokenService.getExpirationDate(request)));
    }

}
