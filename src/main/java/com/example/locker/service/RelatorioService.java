package com.example.locker.service;

import com.example.locker.dto.RelatorioAluguelAtivoDTO;
import com.example.locker.dto.RelatorioOcupacaoLocalizacaoDTO;

import java.util.List;

public interface RelatorioService {
    List<RelatorioAluguelAtivoDTO> gerarRelatorioAlugueisAtivos();
    List<RelatorioOcupacaoLocalizacaoDTO> gerarRelatorioOcupacaoPorLocalizacao();
}