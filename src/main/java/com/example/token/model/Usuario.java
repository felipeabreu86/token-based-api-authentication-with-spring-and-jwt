package com.example.token.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Perfil> perfis = new ArrayList<>();

    public Usuario() {
        super();
    }

    public Usuario(String nome, String email, String senha) {
        this();
        this.nome = nome;
        this.email = email;
        setSenha(senha);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setSenha(String senha) {
        this.senha = new BCryptPasswordEncoder().encode(Objects.requireNonNull(senha));
    }

    public void addPerfil(Perfil perfil) {
        if (!perfis.contains(perfil)) {
            perfis.add(perfil);
        }
    }

    public void removePerfil(Perfil perfil) {
        if (perfis.size() > 1 && perfis.contains(perfil)) {
            perfis.remove(perfil);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder
            .append("Usuário [e-mail=").append(email).append("]")
            .append("[status=").append(isEnabled() ? "ativo" : "inativo").append("]");
        return builder.toString();
    }

}
