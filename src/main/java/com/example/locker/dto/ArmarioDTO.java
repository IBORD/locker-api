package com.example.locker.dto;

import com.example.locker.enums.StatusArmario;
import com.example.locker.enums.TamanhoArmario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar os dados de um armário")
public class ArmarioDTO {

    @Schema(description = "ID único do armário", example = "1")
    private Long id;

    @Schema(description = "Número identificador do armário (visível ao usuário)", example = "A01")
    private String numeroDoArmario;

    @Schema(description = "Tamanho do armário")
    private TamanhoArmario tamanho;

    @Schema(description = "Status atual do armário")
    private StatusArmario status;

    @Schema(description = "ID da localização onde o armário está instalado", example = "1")
    private Long localizacaoId;

    @Schema(description = "Observações sobre o armário (ex: necessita manutenção)", example = "Porta um pouco emperrada")
    private String observacoes;
}