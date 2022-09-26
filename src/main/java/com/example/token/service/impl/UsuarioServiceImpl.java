package com.example.token.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.token.model.Usuario;
import com.example.token.repository.UsuarioRepository;
import com.example.token.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> optUser = userRepository.findByEmail(username);

        if (!optUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        return optUser.get();
    }

}
