package com.example.token.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.token.dto.DadosUsuarioDto;
import com.example.token.model.Usuario;
import com.example.token.service.TokenService;
import com.example.token.util.JsonUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenServiceImpl implements TokenService {

    /**
     * Tempo em milisegundos de vida útil do Token do tipo Bearer.
     */
    @Value("${api.jwt.expiration}")
    private String expiration;

    /**
     * Chave privada a ser utilizada para assinar digitalmente o JWT.
     */
    @Value("${api.jwt.secret}")
    private String secret;

    @Override
    public Optional<String> gerarToken(Authentication authentication) {
        
        Usuario usuario = (Usuario) authentication.getPrincipal();        
        Date dataAtual = new Date();
        Date dataExpiracao = gerarDataExpiracao();
        
        String token = Jwts
                .builder()
                .setIssuer("Api de Autenticação")
                .setSubject(JsonUtil.toJson(new DadosUsuarioDto(usuario)).get())
                .setIssuedAt(dataAtual)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        
        return StringUtils.hasText(token) 
                ? Optional.of(token)
                : Optional.empty();
    }

    @Override
    public boolean isValid(String token) {
        
        boolean isValid = true;
        
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        } catch (Exception e) {
            isValid = false;
        }
        
        return isValid;
    }
    
    @Override
    public Optional<DadosUsuarioDto> getDadosUsuario(String token) {

        Optional<DadosUsuarioDto> dadosUsuario = Optional.empty();

        try {
            String subject = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getSubject();

            if (StringUtils.hasText(subject)) {
                dadosUsuario = JsonUtil.readValue(subject, DadosUsuarioDto.class);
            }
        } catch (Exception e) { }

        return dadosUsuario;
    }
    
    @Override
    public Optional<Date> getExpirationDate(HttpServletRequest request) {

        Optional<String> token = recuperarToken(request);

        return token.isPresent() 
                ? getExpirationDate(token.get()) 
                : Optional.empty();
    }
    
    @Override
    public Optional<Date> getExpirationDate(String token) {
        
        Optional<Date> expirationDate = Optional.empty();

        try {
            expirationDate = Optional
                    .ofNullable(Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getExpiration());
        } catch (Exception e) { }

        return expirationDate;
    }
    
    @Override
    public Optional<String> recuperarToken(HttpServletRequest request) {
        
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        boolean tokenIsValid = !(token == null || token.isEmpty() || token.length() < 8 || !token.startsWith("Bearer "));

        return tokenIsValid 
                ? Optional.of(token.substring(7, token.length()))
                : Optional.empty();
    }

    /**
     * Recupera a data e hora atual e adiciona a quantidade de tempo (em
     * milisegundos) definida para expiração do token.
     * 
     * @return data e hora de expiração do Token.
     */
    private Date gerarDataExpiracao() {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MILLISECOND, Integer.parseInt(expiration));

        return c.getTime();
    }
    
}
