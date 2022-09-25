package com.example.token.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.token.model.Usuario;
import com.example.token.repository.UsuarioRepository;
import com.example.token.service.TokenService;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private UsuarioRepository usuarioRepository;
    
    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        super();
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Optional<String> token = recuperarToken(request);

        if (token.isPresent() && tokenService.isValid(token.get())) {
            autenticarCliente(token.get());
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {
        
        Long idUsuario = tokenService.getIdUsuario(token);
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);

        if (usuario.isPresent()) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    usuario.get(),
                    null, 
                    usuario.get().getAuthorities());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private Optional<String> recuperarToken(HttpServletRequest request) {
        
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        boolean tokenExists = !(token == null || token.isEmpty() || !token.startsWith("Bearer "));

        return tokenExists 
                ? Optional.of(token.substring(7, token.length()))
                : Optional.empty();
    }

}
