package com.example.locker.service;

import com.example.locker.dto.ArmarioDTO;
import com.example.locker.dto.AtualizarArmarioDTO;
import com.example.locker.dto.AtualizarStatusArmarioDTO;
import com.example.locker.dto.CriarArmarioDTO;
import com.example.locker.entity.Armario;
import com.example.locker.entity.Localizacao;
import com.example.locker.exception.ConflitoDeRecursoException;
import com.example.locker.exception.RecursoNaoEncontradoException;
import com.example.locker.repository.ArmarioRepository;
import com.example.locker.repository.LocalizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArmarioServiceImpl implements ArmarioService {

    private final ArmarioRepository armarioRepository;
    private final LocalizacaoRepository localizacaoRepository;

    @Autowired
    public ArmarioServiceImpl(ArmarioRepository armarioRepository,
                              LocalizacaoRepository localizacaoRepository) {
        this.armarioRepository = armarioRepository;
        this.localizacaoRepository = localizacaoRepository;
    }

    private ArmarioDTO paraDTO(Armario armario) {
        if (armario == null) {
            return null;
        }
        Long idDaLocalizacao = (armario.getLocalizacao() != null) ? armario.getLocalizacao().getId() : null;
        return new ArmarioDTO(
                armario.getId(),
                armario.getNumeroDoArmario(),
                armario.getTamanho(),
                armario.getStatus(),
                idDaLocalizacao,
                armario.getObservacoes()
        );
    }



    @Override
    @Transactional
    public ArmarioDTO criarArmario(CriarArmarioDTO criarArmarioDTO) {
        Localizacao localizacao = localizacaoRepository.findById(criarArmarioDTO.getLocalizacaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Localização com ID " + criarArmarioDTO.getLocalizacaoId() + " não encontrada."));

        armarioRepository.findByNumeroDoArmarioAndLocalizacaoId(
                        criarArmarioDTO.getNumeroDoArmario(), criarArmarioDTO.getLocalizacaoId())
                .ifPresent(a -> {
                    throw new ConflitoDeRecursoException("Já existe um armário com o número '" +
                            criarArmarioDTO.getNumeroDoArmario() + "' na localização ID " + criarArmarioDTO.getLocalizacaoId());
                });

        Armario armario = new Armario();
        armario.setNumeroDoArmario(criarArmarioDTO.getNumeroDoArmario());
        armario.setTamanho(criarArmarioDTO.getTamanho());
        armario.setStatus(criarArmarioDTO.getStatus());
        armario.setObservacoes(criarArmarioDTO.getObservacoes());
        armario.setLocalizacao(localizacao);

        Armario armarioSalvo = armarioRepository.save(armario);
        return paraDTO(armarioSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    public ArmarioDTO buscarArmarioPorId(Long id) {
        Armario armario = armarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Armário não encontrado com o ID: " + id));
        return paraDTO(armario);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArmarioDTO> buscarTodosArmarios(Pageable pageable) {
        return armarioRepository.findAll(pageable)
                .map(this::paraDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArmarioDTO> buscarArmariosPorLocalizacao(Long localizacaoId, Pageable pageable) {
        if (!localizacaoRepository.existsById(localizacaoId)) {
            throw new RecursoNaoEncontradoException("Localização com ID " + localizacaoId + " não encontrada.");
        }
        return armarioRepository.findByLocalizacaoId(localizacaoId, pageable)
                .map(this::paraDTO);
    }

    @Override
    @Transactional
    public ArmarioDTO atualizarArmario(Long id, AtualizarArmarioDTO atualizarArmarioDTO) {
        Armario armarioExistente = armarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Armário não encontrado com o ID: " + id));

        if (atualizarArmarioDTO.getStatus() != null) {
            armarioExistente.setStatus(atualizarArmarioDTO.getStatus());
        }
        if (atualizarArmarioDTO.getObservacoes() != null) {
            armarioExistente.setObservacoes(atualizarArmarioDTO.getObservacoes());
        }

        Armario armarioAtualizado = armarioRepository.save(armarioExistente);
        return paraDTO(armarioAtualizado);
    }

    @Override
    @Transactional
    public ArmarioDTO atualizarStatusArmario(Long id, AtualizarStatusArmarioDTO atualizarStatusArmarioDTO) {
        if (!armarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Armário não encontrado com o ID: " + id);
        }

        armarioRepository.atualizarStatus(id, atualizarStatusArmarioDTO.getStatus());

        Armario armarioAtualizado = armarioRepository.findById(id).get();
        return paraDTO(armarioAtualizado);
    }

    @Override
    @Transactional
    public void deletarArmario(Long id) {
        if (!armarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Armário não encontrado com o ID: " + id + " para exclusão.");
        }
        armarioRepository.deleteById(id);
    }
}