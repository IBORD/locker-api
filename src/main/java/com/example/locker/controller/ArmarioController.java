package com.example.locker.controller;

import com.example.locker.dto.ArmarioDTO;
import com.example.locker.dto.CriarArmarioDTO;
import com.example.locker.dto.AtualizarArmarioDTO;
import com.example.locker.dto.AtualizarStatusArmarioDTO;
import com.example.locker.service.ArmarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/armarios")
@Tag(name = "Armários", description = "API para gerenciamento de armários individuais")
public class ArmarioController {

    private static final Logger logger = LoggerFactory.getLogger(ArmarioController.class);
    private final ArmarioService armarioService;

    @Autowired
    public ArmarioController(ArmarioService armarioService) {
        this.armarioService = armarioService;
    }

    @Operation(summary = "Cria um novo armário")
    @PostMapping
    public ResponseEntity<ArmarioDTO> criarArmario(@Valid @RequestBody CriarArmarioDTO criarArmarioDTO) {
        logger.info("Requisição para criar armário: {}", criarArmarioDTO);
        ArmarioDTO novoArmario = armarioService.criarArmario(criarArmarioDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoArmario.getId())
                .toUri();
        logger.info("Armário criado com ID {} e URI {}", novoArmario.getId(), location);
        return ResponseEntity.created(location).body(novoArmario);
    }

    @Operation(summary = "Busca um armário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ArmarioDTO> buscarArmarioPorId(@PathVariable Long id) {
        logger.info("Requisição para buscar armário com ID: {}", id);
        ArmarioDTO armarioDTO = armarioService.buscarArmarioPorId(id);
        return ResponseEntity.ok(armarioDTO);
    }

    @Operation(summary = "Lista todos os armários de forma paginada")
    @GetMapping
    public ResponseEntity<Page<ArmarioDTO>> buscarTodosArmarios(
            @ParameterObject @PageableDefault(size = 10, sort = "numeroDoArmario") Pageable pageable) {
        logger.info("Requisição para listar todos os armários de forma paginada");
        Page<ArmarioDTO> armariosPaginados = armarioService.buscarTodosArmarios(pageable);
        return ResponseEntity.ok(armariosPaginados);
    }

    @Operation(summary = "Lista armários por ID da localização de forma paginada")
    @GetMapping("/por-localizacao/{localizacaoId}")
    public ResponseEntity<Page<ArmarioDTO>> buscarArmariosPorLocalizacao(
            @PathVariable Long localizacaoId,
            @ParameterObject @PageableDefault(size = 10, sort = "numeroDoArmario") Pageable pageable) {
        logger.info("Requisição para listar armários da localização ID {} de forma paginada", localizacaoId);
        Page<ArmarioDTO> armarios = armarioService.buscarArmariosPorLocalizacao(localizacaoId, pageable);
        return ResponseEntity.ok(armarios);
    }

    @Operation(summary = "Atualiza informações de um armário")
    @PutMapping("/{id}")
    public ResponseEntity<ArmarioDTO> atualizarArmario(@PathVariable Long id, @Valid @RequestBody AtualizarArmarioDTO atualizarArmarioDTO) {
        logger.info("Requisição para atualizar armário ID {}: {}", id, atualizarArmarioDTO);
        ArmarioDTO armarioAtualizado = armarioService.atualizarArmario(id, atualizarArmarioDTO);
        return ResponseEntity.ok(armarioAtualizado);
    }

    @Operation(summary = "Atualiza o status de um armário")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ArmarioDTO> atualizarStatusArmario(@PathVariable Long id, @Valid @RequestBody AtualizarStatusArmarioDTO atualizarStatusArmarioDTO) {
        logger.info("Requisição para atualizar status do armário ID {}: {}", id, atualizarStatusArmarioDTO);
        ArmarioDTO armarioAtualizado = armarioService.atualizarStatusArmario(id, atualizarStatusArmarioDTO);
        return ResponseEntity.ok(armarioAtualizado);
    }

    @Operation(summary = "Deleta um armário pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarArmario(@PathVariable Long id) {
        logger.info("Requisição para deletar armário com ID: {}", id);
        armarioService.deletarArmario(id);
        return ResponseEntity.noContent().build();
    }
}