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

import com.example.token.model.Usuario;
import com.example.token.repository.UsuarioRepository;
import com.example.token.service.TokenService;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private UsuarioRepository usuarioRepository;

    /**
     * Construtor.
     * 
     * @param tokenService - regras de negócio relacionadas ao Token.
     * @param usuarioRepository - instância que isola a entidade Usuário do acesso ao banco de dados.
     */
    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
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

        Optional<Long> idUsuario = tokenService.getIdUsuario(token);

        if (idUsuario.isPresent()) {
            Optional<Usuario> usuario = usuarioRepository.findById(idUsuario.get());

            if (usuario.isPresent()) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        usuario.get(), 
                        null, 
                        usuario.get().getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

}
