package com.example.locker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para o relatório de aluguéis ativos com detalhes.")
public class RelatorioAluguelAtivoDTO {

    @Schema(description = "ID do aluguel")
    private Long aluguelId;

    @Schema(description = "Nome do cliente")
    private String nomeCliente;

    @Schema(description = "Email do cliente")
    private String emailCliente;

    @Schema(description = "Número do armário alugado")
    private String numeroArmario;

    @Schema(description = "Nome da localização do armário")
    private String nomeLocalizacao;

    @Schema(description = "Data e hora de início do aluguel")
    private LocalDateTime dataHoraInicio;
}