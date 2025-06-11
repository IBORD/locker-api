package com.example.locker.service;

import com.example.locker.dto.AtualizarLocalizacaoDTO;
import com.example.locker.dto.CriarLocalizacaoDTO;
import com.example.locker.dto.LocalizacaoDTO;
import com.example.locker.entity.Localizacao;
import com.example.locker.exception.RecursoNaoEncontradoException;
import com.example.locker.repository.LocalizacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import correto

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalizacaoServiceImpl implements LocalizacaoService {

    private final LocalizacaoRepository localizacaoRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public LocalizacaoServiceImpl(LocalizacaoRepository localizacaoRepository, ObjectMapper objectMapper) {
        this.localizacaoRepository = localizacaoRepository;
        this.objectMapper = objectMapper;
    }

    private LocalizacaoDTO paraDTO(Localizacao localizacao) {
        return objectMapper.convertValue(localizacao, LocalizacaoDTO.class);
    }

    private Localizacao paraEntidade(CriarLocalizacaoDTO dto) {
        return objectMapper.convertValue(dto, Localizacao.class);
    }

    @Override
    @Transactional
    public LocalizacaoDTO criarLocalizacao(CriarLocalizacaoDTO criarLocalizacaoDTO) {
        Localizacao localizacao = paraEntidade(criarLocalizacaoDTO);
        Localizacao localizacaoSalva = localizacaoRepository.save(localizacao);
        return paraDTO(localizacaoSalva);
    }

    @Override
    @Transactional(readOnly = true)
    public LocalizacaoDTO buscarLocalizacaoPorId(Long id) {
        Localizacao localizacao = localizacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Localização não encontrada com o ID: " + id));
        return paraDTO(localizacao);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocalizacaoDTO> buscarTodasLocalizacoes() {
        return localizacaoRepository.findAll()
                .stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LocalizacaoDTO atualizarLocalizacao(Long id, AtualizarLocalizacaoDTO dto) {
        Localizacao localizacaoExistente = localizacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Localização não encontrada com o ID: " + id));

        if (dto.getNome() != null) {
            localizacaoExistente.setNome(dto.getNome());
        }
        if (dto.getEndereco() != null) {
            localizacaoExistente.setEndereco(dto.getEndereco());
        }
        if (dto.getCidade() != null) {
            localizacaoExistente.setCidade(dto.getCidade());
        }
        if (dto.getDescricao() != null) {
            localizacaoExistente.setDescricao(dto.getDescricao());
        }

        Localizacao localizacaoAtualizada = localizacaoRepository.save(localizacaoExistente);
        return paraDTO(localizacaoAtualizada);
    }

    @Override
    @Transactional
    public void deletarLocalizacao(Long id) {
        if (!localizacaoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Localização não encontrada com o ID: " + id + " para exclusão.");
        }
        localizacaoRepository.deleteById(id);
    }
}