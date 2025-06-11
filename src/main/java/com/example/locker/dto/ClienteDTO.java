package com.example.locker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar os dados de um cliente")
public class ClienteDTO {

    @Schema(description = "ID único do cliente", example = "1")
    private Long id;

    @Schema(description = "Nome completo do cliente", example = "João da Silva")
    private String nome;

    @Schema(description = "Endereço de e-mail do cliente", example = "joao.silva@example.com")
    private String email;

    @Schema(description = "Número de telefone do cliente (opcional)", example = "(11) 98765-4321")
    private String telefone;
}