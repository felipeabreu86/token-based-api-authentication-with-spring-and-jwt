package com.example.token.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Perfil implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    public Perfil() {
        super();
    }

    public Perfil(String authority) {
        this();
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Perfil other = (Perfil) obj;
        return Objects.equals(id, other.id) && Objects.equals(authority, other.authority);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder
            .append("Perfil [id=").append(id).append("]")
            .append("[authority=").append(authority).append("]");
        return builder.toString();
    }

}
