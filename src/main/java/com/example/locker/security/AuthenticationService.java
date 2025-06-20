package com.example.locker.security;

import com.example.locker.entity.UsuarioEntity;
import com.example.locker.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UsuarioService usuarioService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UsuarioEntity> usuarioEntityOptional = usuarioService.findByLogin(username);

        return usuarioEntityOptional
                .orElseThrow(() -> new UsernameNotFoundException("usuario invalido"));
    }
}