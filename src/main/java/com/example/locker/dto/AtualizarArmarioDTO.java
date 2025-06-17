package com.example.locker.dto;

import com.example.locker.enums.StatusArmario;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para atualizar informações de um armário")
public class AtualizarArmarioDTO {

    @Schema(description = "Novo status do armário (opcional)")
    private StatusArmario status;

    @Size(max = 500, message = "As observações não podem exceder 500 caracteres.")
    @Schema(description = "Novas observações sobre o armário (opcional)", example = "Manutenção agendada")
    private String observacoes;

}