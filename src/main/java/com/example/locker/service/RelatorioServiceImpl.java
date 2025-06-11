package com.example.locker.service;

import com.example.locker.dto.RelatorioAluguelAtivoDTO;
import com.example.locker.dto.RelatorioOcupacaoLocalizacaoDTO;
import com.example.locker.enums.StatusAluguel;
import com.example.locker.repository.AluguelRepository;
import com.example.locker.repository.LocalizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RelatorioServiceImpl implements RelatorioService {

    private final AluguelRepository aluguelRepository;
    private final LocalizacaoRepository localizacaoRepository;

    @Autowired
    public RelatorioServiceImpl(AluguelRepository aluguelRepository, LocalizacaoRepository localizacaoRepository) {
        this.aluguelRepository = aluguelRepository;
        this.localizacaoRepository = localizacaoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelatorioAluguelAtivoDTO> gerarRelatorioAlugueisAtivos() {
        return aluguelRepository.findAlugueisAtivosComDetalhes(StatusAluguel.ATIVO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelatorioOcupacaoLocalizacaoDTO> gerarRelatorioOcupacaoPorLocalizacao() {
        return localizacaoRepository.gerarRelatorioOcupacao();
    }
}