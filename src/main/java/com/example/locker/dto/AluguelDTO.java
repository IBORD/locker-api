package com.example.locker.dto;

import com.example.locker.enums.StatusAluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "DTO para representar os dados completos de um aluguel")
public class AluguelDTO {

    @Schema(description = "ID único do aluguel")
    private Long id;

    @Schema(description = "Dados do cliente que realizou o aluguel")
    private ClienteDTO cliente;
    @Schema(description = "Dados do armário alugado")
    private ArmarioDTO armario;

    @Schema(description = "Data e hora de início do aluguel")
    private LocalDateTime dataHoraInicio;

    @Schema(description = "Data e hora de término do aluguel")
    private LocalDateTime dataHoraFim;

    @Schema(description = "Código de acesso para o armário")
    private String codigoAcesso;

    @Schema(description = "Status atual do aluguel")
    private StatusAluguel status;

    @Schema(description = "Preço do aluguel")
    private BigDecimal preco;
}