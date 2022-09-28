package com.example.token.dto;

import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DadosAutenticacaoDto {

    private String email;

    @JsonProperty("expiration_date")
    private Date dataExpiracao;

    public DadosAutenticacaoDto() {
        super();
    }

    public DadosAutenticacaoDto(String emailUsuarioAutenticado, Optional<Date> tokenExpirationDate) {
        this();

        setEmail(emailUsuarioAutenticado);

        if (tokenExpirationDate.isPresent()) {
            setDataExpiracao(tokenExpirationDate.get());
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

}
