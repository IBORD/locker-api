package com.example.locker.controller;

import com.example.locker.dto.AtualizarLocalizacaoDTO;
import com.example.locker.dto.CriarLocalizacaoDTO;
import com.example.locker.dto.LocalizacaoDTO;
import com.example.locker.service.LocalizacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/localizacoes")
@Tag(name = "Localizações", description = "API para gerenciamento de localizações dos guarda-volumes")
public class LocalizacaoController {

    private static final Logger logger = LoggerFactory.getLogger(LocalizacaoController.class);

    private final LocalizacaoService localizacaoService;

    @Autowired
    public LocalizacaoController(LocalizacaoService localizacaoService) {
        this.localizacaoService = localizacaoService;
    }

    @Operation(summary = "Cria uma nova localização")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Localização criada com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocalizacaoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<LocalizacaoDTO> criarLocalizacao(@Valid @RequestBody CriarLocalizacaoDTO criarLocalizacaoDTO) {
        logger.info("Recebida requisição para criar localização com dados: {}", criarLocalizacaoDTO);

        try {
            LocalizacaoDTO novaLocalizacao = localizacaoService.criarLocalizacao(criarLocalizacaoDTO);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(novaLocalizacao.getId())
                    .toUri();


            logger.info("Localização criada com sucesso. ID: {}, URI: {}", novaLocalizacao.getId(), location);

            return ResponseEntity.created(location).body(novaLocalizacao);
        } catch (Exception e) {
            logger.error("Erro ao tentar criar localização com dados: {}. Erro: {}", criarLocalizacaoDTO, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(summary = "Busca uma localização pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Localização encontrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocalizacaoDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Localização não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LocalizacaoDTO> buscarLocalizacaoPorId(
            @Parameter(description = "ID da localização a ser buscada", required = true) @PathVariable Long id) {
        logger.info("Recebida requisição para buscar localização com ID: {}", id);
        LocalizacaoDTO localizacaoDTO = localizacaoService.buscarLocalizacaoPorId(id);
        logger.info("Localização com ID {} encontrada: {}", id, localizacaoDTO);
        return ResponseEntity.ok(localizacaoDTO);
    }

    @Operation(summary = "Lista todas as localizações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de localizações recuperada com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class, subTypes = {LocalizacaoDTO.class})) })
    })
    @GetMapping
    public ResponseEntity<List<LocalizacaoDTO>> buscarTodasLocalizacoes() {
        logger.info("Recebida requisição para listar todas as localizações");
        List<LocalizacaoDTO> localizacoes = localizacaoService.buscarTodasLocalizacoes();
        logger.info("Retornando {} localizações.", localizacoes.size());
        return ResponseEntity.ok(localizacoes);
    }

    @Operation(summary = "Atualiza uma localização existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Localização atualizada com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocalizacaoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Localização não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LocalizacaoDTO> atualizarLocalizacao(
            @Parameter(description = "ID da localização a ser atualizada", required = true) @PathVariable Long id,
            @Valid @RequestBody AtualizarLocalizacaoDTO atualizarLocalizacaoDTO) {
        logger.info("Recebida requisição para atualizar localização com ID: {}. Dados para atualização: {}", id, atualizarLocalizacaoDTO);
        LocalizacaoDTO localizacaoAtualizada = localizacaoService.atualizarLocalizacao(id, atualizarLocalizacaoDTO);
        logger.info("Localização com ID {} atualizada com sucesso: {}", id, localizacaoAtualizada);
        return ResponseEntity.ok(localizacaoAtualizada);
    }

    @Operation(summary = "Deleta uma localização pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Localização deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Localização não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLocalizacao(
            @Parameter(description = "ID da localização a ser deletada", required = true) @PathVariable Long id) {
        logger.info("Recebida requisição para deletar localização com ID: {}", id);
        localizacaoService.deletarLocalizacao(id);
        logger.info("Localização com ID {} deletada com sucesso.", id);
        return ResponseEntity.noContent().build();
    }
}