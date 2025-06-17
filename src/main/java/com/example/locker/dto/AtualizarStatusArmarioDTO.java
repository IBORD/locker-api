package com.example.locker.dto;

import com.example.locker.enums.StatusArmario;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para atualizar o status de um armário")
public class AtualizarStatusArmarioDTO {

    @NotNull(message = "O novo status do armário não pode ser nulo.")
    @Schema(description = "Novo status para o armário", example = "MANUTENCAO", requiredMode = Schema.RequiredMode.REQUIRED)
    private StatusArmario status;
}