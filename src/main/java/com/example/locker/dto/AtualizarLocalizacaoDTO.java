package com.example.locker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para atualizar uma localização existente")
public class AtualizarLocalizacaoDTO {

    @Size(min = 3, max = 255, message = "O nome da localização deve ter entre 3 e 255 caracteres, se fornecido.")
    @Schema(description = "Novo nome da localização (opcional, mas se fornecido, deve ser válido)", example = "Estação da Luz - Plataforma 2")
    private String nome;

    @Size(max = 255, message = "O endereço não pode exceder 255 caracteres, se fornecido.")
    @Schema(description = "Novo endereço da localização", example = "Praça da Luz, 100 - Luz")
    private String endereco;

    @Size(max = 100, message = "A cidade não pode exceder 100 caracteres, se fornecida.")
    @Schema(description = "Nova cidade da localização", example = "São Paulo")
    private String cidade;

    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres, se fornecida.")
    @Schema(description = "Nova descrição ou detalhes adicionais", example = "Entrada pela Rua Mauá")
    private String descricao;
}