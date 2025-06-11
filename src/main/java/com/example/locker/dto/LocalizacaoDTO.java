package com.example.locker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar os dados de uma localização")
public class LocalizacaoDTO {

    @Schema(description = "ID único da localização", example = "1")
    private Long id;

    @Schema(description = "Nome da localização", example = "Aeroporto GRU - Terminal 1, Seção A")
    private String nome;

    @Schema(description = "Endereço da localização", example = "Rodovia Hélio Smidt, s/nº - Cumbica")
    private String endereco;

    @Schema(description = "Cidade da localização", example = "Guarulhos")
    private String cidade;

    @Schema(description = "Descrição ou detalhes adicionais sobre a localização", example = "Próximo ao portão de embarque 7")
    private String descricao;
}