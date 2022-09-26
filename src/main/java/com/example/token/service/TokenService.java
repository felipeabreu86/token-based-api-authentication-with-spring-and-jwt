package com.example.token.service;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public interface TokenService {

    /**
     * Gera um Token do tipo Bearer.
     * 
     * @param authentication - instância de Authentication que contêm os dados do
     *                       usuário a ser autenticado.
     * @return Token do tipo Bearer ou vazio (Optinal.empty()).
     */
    Optional<String> gerarToken(Authentication authentication);

    /**
     * Verifica se o token passado por parâmetro é válido.
     * 
     * @param token - Token do tipo Bearer.
     * @return True ou False
     */
    boolean isValid(String token);

    /**
     * Retorna o Id do usuário recuperado por meio do Token passado por parâmetro.
     * 
     * @param token - Token do tipo Bearer.
     * @return Id do usuário ou vazio (Optinal.empty()).
     */
    Optional<Long> getIdUsuario(String token);

    /**
     * Obtém o Token por meio do parâmetro de cabeçalho (header) "Authorization" da
     * requisição HTTP e retorna a data de expiração deste.
     * 
     * @param request - requisição HTTP.
     * @return Data de expiração do Token ou vazio (Optinal.empty()).
     */
    Optional<Date> getExpirationDate(HttpServletRequest request);

    /**
     * Retorna a data de expiração do Token passado por parâmetro.
     * 
     * @param token - Token do tipo Bearer.
     * @return Data de expiração do Token ou vazio (Optinal.empty()).
     */
    Optional<Date> getExpirationDate(String token);

    /**
     * Obtém o Token por meio do parâmetro de cabeçalho (header) "Authorization" da
     * requisição HTTP.
     * 
     * @param request - requisição HTTP.
     * @return Token do tipo Bearer ou vazio (Optinal.empty()).
     */
    Optional<String> recuperarToken(HttpServletRequest request);

}
