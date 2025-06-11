package com.example.locker.service;

import com.example.locker.dto.ArmarioDTO;
import com.example.locker.dto.CriarArmarioDTO;
import com.example.locker.dto.AtualizarArmarioDTO;
import com.example.locker.dto.AtualizarStatusArmarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArmarioService {
    ArmarioDTO criarArmario(CriarArmarioDTO criarArmarioDTO);
    ArmarioDTO buscarArmarioPorId(Long id);
    Page<ArmarioDTO> buscarTodosArmarios(Pageable pageable);
    Page<ArmarioDTO> buscarArmariosPorLocalizacao(Long localizacaoId, Pageable pageable);
    ArmarioDTO atualizarArmario(Long id, AtualizarArmarioDTO atualizarArmarioDTO);
    ArmarioDTO atualizarStatusArmario(Long id, AtualizarStatusArmarioDTO atualizarStatusArmarioDTO);
    void deletarArmario(Long id);
}