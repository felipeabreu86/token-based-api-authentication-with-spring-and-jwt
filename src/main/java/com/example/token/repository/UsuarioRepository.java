package com.example.token.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.token.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}
