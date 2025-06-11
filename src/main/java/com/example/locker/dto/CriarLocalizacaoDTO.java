package com.example.locker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criar uma nova localização")
public class CriarLocalizacaoDTO {

    @NotBlank(message = "O nome da localização não pode estar em branco.")
    @Size(min = 3, max = 255, message = "O nome da localização deve ter entre 3 e 255 caracteres.")
    @Schema(description = "Nome da localização", example = "Estação da Luz - Plataforma Central", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @Size(max = 255, message = "O endereço não pode exceder 255 caracteres.")
    @Schema(description = "Endereço da localização", example = "Praça da Luz, s/n - Luz")
    private String endereco;

    @Size(max = 100, message = "A cidade não pode exceder 100 caracteres.")
    @Schema(description = "Cidade da localização", example = "São Paulo")
    private String cidade;

    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres.")
    @Schema(description = "Descrição ou detalhes adicionais", example = "Ao lado da bilheteria principal")
    private String descricao;
}