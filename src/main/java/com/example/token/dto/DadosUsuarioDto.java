package com.example.token.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.example.token.model.Perfil;
import com.example.token.model.Usuario;
import com.example.token.util.CastUtils;

public class DadosUsuarioDto {

    private String email;

    private Collection<Perfil> perfis = new ArrayList<>();

    public DadosUsuarioDto() {
        super();
    }

    public DadosUsuarioDto(Usuario usuario) {
        this.email = usuario.getUsername();
        this.perfis = CastUtils.castList(Perfil.class, usuario.getAuthorities());
    }

    public String getEmail() {
        return email;
    }

    public Collection<Perfil> getPerfis() {
        return perfis;
    }

}
