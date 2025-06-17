package com.example.locker.service;

import com.example.locker.dto.AluguelDTO;
import com.example.locker.dto.ArmarioDTO;
import com.example.locker.dto.ClienteDTO;
import com.example.locker.dto.CriarAluguelDTO;
import com.example.locker.entity.Aluguel;
import com.example.locker.entity.Armario;
import com.example.locker.entity.Cliente;
import com.example.locker.enums.StatusAluguel;
import com.example.locker.enums.StatusArmario;
import com.example.locker.exception.ConflitoDeRecursoException;
import com.example.locker.exception.RecursoNaoEncontradoException;
import com.example.locker.exception.RegraDeNegocioException;
import com.example.locker.repository.AluguelRepository;
import com.example.locker.repository.ArmarioRepository;
import com.example.locker.repository.ClienteRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AluguelServiceImpl implements AluguelService {

    private final AluguelRepository aluguelRepository;
    private final ArmarioRepository armarioRepository;
    private final ClienteRepository clienteRepository;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    public AluguelServiceImpl(AluguelRepository aluguelRepository, ArmarioRepository armarioRepository, ClienteRepository clienteRepository, EmailService emailService, UsuarioService usuarioService) {
        this.aluguelRepository = aluguelRepository;
        this.armarioRepository = armarioRepository;
        this.clienteRepository = clienteRepository;
        this.emailService = emailService;
        this.usuarioService = usuarioService;
    }


    private AluguelDTO paraDTORico(Aluguel aluguel) {
        AluguelDTO dto = new AluguelDTO();
        dto.setId(aluguel.getId());
        dto.setDataHoraInicio(aluguel.getDataHoraInicio());
        dto.setDataHoraFim(aluguel.getDataHoraFim());
        dto.setCodigoAcesso(aluguel.getCodigoAcesso());
        dto.setStatus(aluguel.getStatus());
        dto.setPreco(aluguel.getPreco());

        if (aluguel.getCliente() != null) {
            Cliente cliente = aluguel.getCliente();
            dto.setCliente(new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getTelefone()));
        }

        if (aluguel.getArmario() != null) {
            Armario armario = aluguel.getArmario();
            dto.setArmario(new ArmarioDTO(armario.getId(), armario.getNumeroDoArmario(), armario.getTamanho(), armario.getStatus(), armario.getLocalizacao().getId(), armario.getObservacoes()));
        }

        return dto;
    }

    @Override
    @Transactional
    public AluguelDTO criarAluguel(CriarAluguelDTO criarAluguelDTO) {

        Cliente cliente = clienteRepository.findById(criarAluguelDTO.getClienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com ID: " + criarAluguelDTO.getClienteId()));

        Armario armario = armarioRepository.findById(criarAluguelDTO.getArmarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Armário não encontrado com ID: " + criarAluguelDTO.getArmarioId()));

        if (armario.getStatus() != StatusArmario.DISPONIVEL) {
            throw new ConflitoDeRecursoException("O armário ID " + armario.getId() + " não está disponível. Status atual: " + armario.getStatus());
        }

        Aluguel novoAluguel = new Aluguel();

        novoAluguel.setCliente(cliente);
        novoAluguel.setArmario(armario);
        novoAluguel.setDataHoraInicio(LocalDateTime.now());
        novoAluguel.setStatus(StatusAluguel.ATIVO);
        novoAluguel.setCodigoAcesso(UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        novoAluguel.setPreco(BigDecimal.ZERO);

        Aluguel aluguelSalvo = aluguelRepository.save(novoAluguel);

        armarioRepository.atualizarStatus(armario.getId(), StatusArmario.OCUPADO);

        AluguelDTO aluguelDTO = paraDTORico(aluguelSalvo);
        emailService.enviarEmailConfirmacaoAluguel(aluguelDTO);

        return aluguelDTO;
    }

    @Override
    @Transactional
    public AluguelDTO finalizarAluguel(Long aluguelId) {
        Aluguel aluguel = aluguelRepository.findById(aluguelId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluguel não encontrado com ID: " + aluguelId));

        if (aluguel.getStatus() != StatusAluguel.ATIVO) {
            throw new ConflitoDeRecursoException("Não é possível finalizar um aluguel que não está ATIVO. Status atual: " + aluguel.getStatus());
        }

        aluguel.setStatus(StatusAluguel.CONCLUIDO);
        aluguel.setDataHoraFim(LocalDateTime.now());

        Aluguel aluguelFinalizado = aluguelRepository.save(aluguel);

        armarioRepository.atualizarStatus(aluguel.getArmario().getId(), StatusArmario.DISPONIVEL);

        return paraDTORico(aluguelFinalizado);
    }

    @Override
    @Transactional(readOnly = true)
    public AluguelDTO buscarAluguelPorId(Long id) {
        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluguel não encontrado com ID: " + id));
        return paraDTORico(aluguel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AluguelDTO> buscarAlugueisPorCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com ID: " + clienteId));

        try {
            String emailClienteSolicitado = cliente.getEmail();
            String loginUsuarioLogado = usuarioService.getLoggedUser().getLogin();

            if (!loginUsuarioLogado.equals(emailClienteSolicitado)) {
                throw new AccessDeniedException("Acesso negado. Você não pode visualizar aluguéis de outro cliente.");
            }
        } catch (RegraDeNegocioException e) {
            throw new AccessDeniedException("Acesso negado. Não foi possível verificar a identidade do usuário.");
        }

        return aluguelRepository.findByClienteId(clienteId)
                .stream()
                .map(this::paraDTORico)
                .collect(Collectors.toList());
    }
}