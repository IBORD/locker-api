package com.example.locker.controller;

import com.example.locker.dto.*;
import com.example.locker.entity.UsuarioEntity;
import com.example.locker.exception.RegraDeNegocioException;
import com.example.locker.security.TokenService;
import com.example.locker.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    public final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;

    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getSenha()
                );
        Authentication authentication =
                authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UsuarioEntity usuarioValidado = (UsuarioEntity) authentication.getPrincipal();

        return tokenService.generateToken(usuarioValidado);
    }

    @GetMapping("/usuario-logado")
    public UsuarioDTO getUsuarioLogado() throws RegraDeNegocioException {
        return usuarioService.getLoggedUser();
    }

    @PostMapping("/create-user")
    public ResponseEntity<UsuarioDTO> createUser(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioDTO novoUsuario = usuarioService.create(usuarioCreateDTO);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }
    @PutMapping("/trocar-senha")
    public ResponseEntity<String> trocarSenha(@Valid @RequestBody TrocaSenhaDTO trocaSenhaDTO) throws RegraDeNegocioException {
        String mensagem = usuarioService.trocarSenha(trocaSenhaDTO);
        return ResponseEntity.ok(mensagem);
    }

    @PutMapping("atualizar-usuario/{idUsuario}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Integer idUsuario, @Valid @RequestBody AtualizarUsuarioDTO atualizarUsuarioDTO) throws RegraDeNegocioException {
        UsuarioDTO usuarioAtualizado = usuarioService.atualizarUsuario(idUsuario, atualizarUsuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("desativar-usuario/{idUsuario}")
    public ResponseEntity<Void> desativarUsuario(@PathVariable Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.desativarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }
}