package com.example.locker.controller;

import com.example.locker.dto.RelatorioAluguelAtivoDTO;
import com.example.locker.dto.RelatorioOcupacaoLocalizacaoDTO;
import com.example.locker.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/relatorios")
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios do sistema")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @Autowired
    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/alugueis-ativos")
    @Operation(summary = "Gera um relatório de aluguéis ativos")
    @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class, subTypes = {RelatorioAluguelAtivoDTO.class})))
    public ResponseEntity<List<RelatorioAluguelAtivoDTO>> getRelatorioAlugueisAtivos() {
        List<RelatorioAluguelAtivoDTO> relatorio = relatorioService.gerarRelatorioAlugueisAtivos();
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/ocupacao-localizacoes")
    @Operation(summary = "Gera um relatório de ocupação de armários por localização")
    @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class, subTypes = {RelatorioOcupacaoLocalizacaoDTO.class})))
    public ResponseEntity<List<RelatorioOcupacaoLocalizacaoDTO>> getRelatorioOcupacaoPorLocalizacao() {
        List<RelatorioOcupacaoLocalizacaoDTO> relatorio = relatorioService.gerarRelatorioOcupacaoPorLocalizacao();
        return ResponseEntity.ok(relatorio);
    }
}