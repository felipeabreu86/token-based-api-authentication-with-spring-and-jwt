package com.example.token.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.token.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Realiza a busca por usuário no banco de dados filtrando pelo email.
     * 
     * @param email - email do usuário.
     * @return Usuário encontrado no banco de dados ou vazio (Optional.empty()).
     */
    Optional<Usuario> findByEmail(String email);

}
