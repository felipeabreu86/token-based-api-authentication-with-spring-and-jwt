package com.example.token.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.token.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenService {

    @Value("${api.jwt.expiration}")
    private String expiration;

    @Value("${api.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        
        String idUsuario = String.valueOf(((Usuario) authentication.getPrincipal()).getId());

        Date dataAtual = new Date();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MILLISECOND, Integer.parseInt(expiration));
        Date dataExpiracao = c.getTime();

        return Jwts
                .builder()
                .setIssuer("Api de Autenticação")
                .setSubject(idUsuario)
                .setIssuedAt(dataAtual)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValid(String token) {
        
        boolean isValid = true;
        
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | ExpiredJwtException
                | IllegalArgumentException e) {
            isValid = false;
        }
        
        return isValid;
    }

    public Long getIdUsuario(String token) {
        
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

}
