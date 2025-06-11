package com.example.locker.service;

import com.example.locker.dto.AtualizarClienteDTO;
import com.example.locker.dto.ClienteDTO;
import com.example.locker.dto.CriarClienteDTO;
import com.example.locker.entity.Cliente;
import com.example.locker.exception.ConflitoDeRecursoException;
import com.example.locker.exception.RecursoNaoEncontradoException;
import com.example.locker.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository,
                              ObjectMapper objectMapper,
                              EmailService emailService) {
        this.clienteRepository = clienteRepository;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    private ClienteDTO paraDTO(Cliente cliente) {
        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }

    private Cliente paraEntidade(CriarClienteDTO dto) {
        return objectMapper.convertValue(dto, Cliente.class);
    }

    @Override
    @Transactional
    public ClienteDTO criarCliente(CriarClienteDTO criarClienteDTO) {
        logger.info("Tentando criar cliente com email: {}", criarClienteDTO.getEmail());

        clienteRepository.findByEmail(criarClienteDTO.getEmail())
                .ifPresent(c -> {
                    logger.warn("Conflito: Email {} já existe.", criarClienteDTO.getEmail());
                    throw new ConflitoDeRecursoException("Já existe um cliente cadastrado com o e-mail: " + criarClienteDTO.getEmail());
                });

        Cliente cliente = paraEntidade(criarClienteDTO);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        ClienteDTO clienteSalvoDTO = paraDTO(clienteSalvo);

        try {
            emailService.enviarEmailBoasVindasCliente(clienteSalvoDTO);
        } catch (Exception e) {
            logger.error("Erro ao tentar agendar envio de email de boas-vindas para {}: {}", clienteSalvoDTO.getEmail(), e.getMessage());
        }
        logger.info("Cliente {} criado com sucesso.", clienteSalvoDTO.getEmail());
        return clienteSalvoDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com o ID: " + id));
        return paraDTO(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO buscarClientePorEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com o e-mail: " + email));
        return paraDTO(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> buscarTodosClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(this::paraDTO);
    }

    @Override
    @Transactional
    public ClienteDTO atualizarCliente(Long id, AtualizarClienteDTO atualizarClienteDTO) {
        logger.info("Tentando atualizar cliente com ID: {}", id);
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cliente com ID {} não encontrado para atualização.", id);
                    return new RecursoNaoEncontradoException("Cliente não encontrado com o ID: " + id);
                });

        if (atualizarClienteDTO.getEmail() != null && !atualizarClienteDTO.getEmail().equals(clienteExistente.getEmail())) {
            clienteRepository.findByEmail(atualizarClienteDTO.getEmail())
                    .ifPresent(c -> {
                        if (!c.getId().equals(id)) {
                            logger.warn("Conflito: Email {} já está em uso por outro cliente.", atualizarClienteDTO.getEmail());
                            throw new ConflitoDeRecursoException("O e-mail " + atualizarClienteDTO.getEmail() + " já está em uso por outro cliente.");
                        }
                    });
            clienteExistente.setEmail(atualizarClienteDTO.getEmail());
        }

        if (atualizarClienteDTO.getNome() != null) {
            clienteExistente.setNome(atualizarClienteDTO.getNome());
        }

        if (atualizarClienteDTO.getTelefone() != null) {
            clienteExistente.setTelefone(atualizarClienteDTO.getTelefone().isEmpty() ? null : atualizarClienteDTO.getTelefone());
        }

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        ClienteDTO clienteAtualizadoDTO = paraDTO(clienteAtualizado);

        try {
            emailService.enviarEmailAtualizacaoCliente(clienteAtualizadoDTO);
        } catch (Exception e) {
            logger.error("Erro ao tentar agendar envio de email de atualização para {}: {}", clienteAtualizadoDTO.getEmail(), e.getMessage());
        }
        logger.info("Cliente {} atualizado com sucesso.", clienteAtualizadoDTO.getEmail());
        return clienteAtualizadoDTO;
    }

    @Override
    @Transactional
    public void deletarCliente(Long id) {
        logger.info("Tentando deletar cliente com ID: {}", id);

        Cliente clienteParaExcluir = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cliente com ID {} não encontrado para exclusão.", id);
                    return new RecursoNaoEncontradoException("Cliente não encontrado com o ID: " + id + " para exclusão.");
                });

        ClienteDTO clienteExcluidoDTO = paraDTO(clienteParaExcluir);

        clienteRepository.deleteById(id);
        logger.info("Cliente com ID {} deletado do banco de dados.", id);

        try {
            emailService.enviarEmailExclusaoCliente(clienteExcluidoDTO);
        } catch (Exception e) {
            logger.error("Erro ao tentar agendar envio de email de exclusão para {}: {}", clienteExcluidoDTO.getEmail(), e.getMessage());
        }
    }
}