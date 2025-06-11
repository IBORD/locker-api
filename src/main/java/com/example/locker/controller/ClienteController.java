package com.example.locker.controller;

import com.example.locker.dto.ClienteDTO;
import com.example.locker.dto.CriarClienteDTO;
import com.example.locker.dto.AtualizarClienteDTO;
import com.example.locker.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "API para gerenciamento de clientes do serviço")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Cria um novo cliente")
    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@Valid @RequestBody CriarClienteDTO criarClienteDTO) {
        logger.info("Requisição para criar cliente: {}", criarClienteDTO);
        ClienteDTO novoCliente = clienteService.criarCliente(criarClienteDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoCliente.getId())
                .toUri();
        logger.info("Cliente criado com ID {} e URI {}", novoCliente.getId(), location);
        return ResponseEntity.created(location).body(novoCliente);
    }

    @Operation(summary = "Busca um cliente pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Long id) {
        logger.info("Requisição para buscar cliente com ID: {}", id);
        ClienteDTO clienteDTO = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(clienteDTO);
    }

    @Operation(summary = "Busca um cliente pelo e-mail")
    @GetMapping("/por-email")
    public ResponseEntity<ClienteDTO> buscarClientePorEmail(@RequestParam String email) {
        logger.info("Requisição para buscar cliente com e-mail: {}", email);
        ClienteDTO clienteDTO = clienteService.buscarClientePorEmail(email);
        return ResponseEntity.ok(clienteDTO);
    }

    @Operation(summary = "Lista todos os clientes de forma paginada")
    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> buscarTodosClientes(
            @ParameterObject @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        logger.info("Requisição para listar todos os clientes de forma paginada");
        Page<ClienteDTO> clientes = clienteService.buscarTodosClientes(pageable);
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Atualiza um cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody AtualizarClienteDTO atualizarClienteDTO) {
        logger.info("Requisição para atualizar cliente ID {}: {}", id, atualizarClienteDTO);
        ClienteDTO clienteAtualizado = clienteService.atualizarCliente(id, atualizarClienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @Operation(summary = "Deleta um cliente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        logger.info("Requisição para deletar cliente com ID: {}", id);
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}