package com.example.token.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.token.dto.DadosUsuarioDto;
import com.example.token.service.TokenService;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    /**
     * Construtor.
     * 
     * @param tokenService - regras de negócio relacionadas ao Token.
     */
    public AutenticacaoViaTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {

        Optional<String> token = tokenService.recuperarToken(request);

        if (token.isPresent() && tokenService.isValid(token.get())) {
            autenticarCliente(token.get());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Método responsável por alterar o usuário atualmente autenticado ou remover as
     * informações de autenticação.
     * 
     * @param token - Token do tipo Bearer.
     */
    private void autenticarCliente(String token) {

        Optional<DadosUsuarioDto> dadosUsuario = tokenService.getDadosUsuario(token);
        
        if (dadosUsuario.isPresent()) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    dadosUsuario.get().getEmail(),
                    null, 
                    dadosUsuario.get().getPerfis());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

}
