package com.example.locker.service;

import com.example.locker.dto.AtualizarUsuarioDTO;
import com.example.locker.dto.TrocaSenhaDTO;
import com.example.locker.dto.UsuarioCreateDTO;
import com.example.locker.dto.UsuarioDTO;
import com.example.locker.entity.CargoEntity;
import com.example.locker.entity.UsuarioEntity;
import com.example.locker.repository.CargoRepository;
import com.example.locker.repository.UsuarioRepository;
import com.example.locker.exception.RegraDeNegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final CargoRepository cargoRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public UsuarioDTO getLoggedUser() throws RegraDeNegocioException {
        UsuarioEntity usuarioLogado = findById(getIdLoggedUser());
        return retornarDTO(usuarioLogado);
    }

    public Integer getIdLoggedUser() {
        String findUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Integer.parseInt(findUserId);
    }

    public UsuarioEntity findById(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));
    }

    private UsuarioDTO retornarDTO(UsuarioEntity usuarioEntity) {
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        if (usuarioRepository.findByLogin(usuarioCreateDTO.getLogin()).isPresent()) {
            throw new RegraDeNegocioException("Login já cadastrado!");
        }

        Set<CargoEntity> cargos = usuarioCreateDTO.getCargos().stream()
                .map(idCargo -> {
                    try {
                        return cargoRepository.findById(idCargo)
                                .orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado: " + idCargo));
                    } catch (RegraDeNegocioException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setLogin(usuarioCreateDTO.getLogin());
        usuarioEntity.setSenha(passwordEncoder.encode(usuarioCreateDTO.getSenha()));
        usuarioEntity.setCargos(cargos);
        usuarioEntity.setAtivo(true);

        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioEntity);
        return objectMapper.convertValue(usuarioSalvo, UsuarioDTO.class);
    }

    public String trocarSenha(TrocaSenhaDTO trocaSenhaDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioLogado = findById(getIdLoggedUser());

        if (!passwordEncoder.matches(trocaSenhaDTO.getSenhaAtual(), usuarioLogado.getSenha())) {
            throw new RegraDeNegocioException("Senha atual incorreta.");
        }

        usuarioLogado.setSenha(passwordEncoder.encode(trocaSenhaDTO.getNovaSenha()));
        usuarioRepository.save(usuarioLogado);

        return "Senha alterada com sucesso!";
    }

    public UsuarioDTO atualizarUsuario(Integer idUsuario, AtualizarUsuarioDTO atualizarUsuarioDTO) throws RegraDeNegocioException {
        UsuarioEntity usuario = findById(idUsuario);

        if (atualizarUsuarioDTO.getLogin() != null) {
            usuario.setLogin(atualizarUsuarioDTO.getLogin());
        }

        if (atualizarUsuarioDTO.getCargos() != null && !atualizarUsuarioDTO.getCargos().isEmpty()) {
            Set<CargoEntity> novosCargos = atualizarUsuarioDTO.getCargos().stream()
                    .map(idCargo -> {
                        try {
                            return cargoRepository.findById(idCargo)
                                    .orElseThrow(() -> new RegraDeNegocioException("Cargo com ID " + idCargo + " não encontrado."));
                        } catch (RegraDeNegocioException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toSet());
            usuario.setCargos(novosCargos);
        }

        usuarioRepository.save(usuario);
        return retornarDTO(usuario);
    }

    public void desativarUsuario(Integer idUsuario) throws RegraDeNegocioException {
        if (idUsuario.equals(getIdLoggedUser())) {
            throw new RegraDeNegocioException("Não é possível desativar o próprio usuário.");
        }

        UsuarioEntity usuario = findById(idUsuario);
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
}