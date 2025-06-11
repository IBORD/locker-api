package com.example.locker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para o relatório de ocupação de armários por localização.")
public class RelatorioOcupacaoLocalizacaoDTO {

    @Schema(description = "ID da localização")
    private Long localizacaoId;

    @Schema(description = "Nome da localização")
    private String nomeLocalizacao;

    @Schema(description = "Total de armários na localização")
    private Long totalArmarios;

    @Schema(description = "Quantidade de armários com status DISPONIVEL")
    private Long disponiveis;

    @Schema(description = "Quantidade de armários com status OCUPADO")
    private Long ocupados;

    @Schema(description = "Quantidade de armários com status MANUTENCAO")
    private Long emManutencao;

    @Schema(description = "Quantidade de armários com status FORA_DE_SERVICO")
    private Long foraDeServico;
}