package com.example.locker.dto;

import com.example.locker.enums.StatusArmario;
import com.example.locker.enums.TamanhoArmario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criar um novo armário")
public class CriarArmarioDTO {

    @NotBlank(message = "O número do armário não pode estar em branco.")
    @Size(min = 1, max = 50, message = "O número do armário deve ter entre 1 e 50 caracteres.")
    @Schema(description = "Número identificador do armário", example = "B12", requiredMode = Schema.RequiredMode.REQUIRED)
    private String numeroDoArmario;

    @NotNull(message = "O tamanho do armário não pode ser nulo.")
    @Schema(description = "Tamanho do armário (PEQUENO, MEDIO, GRANDE)", example = "MEDIO", requiredMode = Schema.RequiredMode.REQUIRED)
    private TamanhoArmario tamanho;

    @NotNull(message = "O status inicial do armário não pode ser nulo.")
    @Schema(description = "Status inicial do armário (normalmente DISPONIVEL ao criar)", example = "DISPONIVEL", requiredMode = Schema.RequiredMode.REQUIRED)
    private StatusArmario status;

    @NotNull(message = "O ID da localização não pode ser nulo.")
    @Schema(description = "ID da localização onde o armário será instalado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long localizacaoId;

    @Size(max = 500, message = "As observações não podem exceder 500 caracteres.")
    @Schema(description = "Observações iniciais sobre o armário (opcional)", example = "Armário novo")
    private String observacoes;
}