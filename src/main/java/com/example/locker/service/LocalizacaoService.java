package com.example.locker.service;

import com.example.locker.dto.AtualizarLocalizacaoDTO;
import com.example.locker.dto.CriarLocalizacaoDTO;
import com.example.locker.dto.LocalizacaoDTO;

import java.util.List;

public interface LocalizacaoService {
    LocalizacaoDTO criarLocalizacao(CriarLocalizacaoDTO criarLocalizacaoDTO);
    LocalizacaoDTO buscarLocalizacaoPorId(Long id);
    List<LocalizacaoDTO> buscarTodasLocalizacoes();
    LocalizacaoDTO atualizarLocalizacao(Long id, AtualizarLocalizacaoDTO atualizarLocalizacaoDTO);
    void deletarLocalizacao(Long id);
}