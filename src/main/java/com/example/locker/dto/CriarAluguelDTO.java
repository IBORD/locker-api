package com.example.locker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "DTO para criar um novo aluguel")
public class CriarAluguelDTO {

    @NotNull(message = "O ID do cliente não pode ser nulo.")
    @Schema(description = "ID do cliente que está realizando o aluguel", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long clienteId;

    @NotNull(message = "O ID do armário não pode ser nulo.")
    @Schema(description = "ID do armário a ser alugado", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long armarioId;
}