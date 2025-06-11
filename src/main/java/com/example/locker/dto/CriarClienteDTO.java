package com.example.locker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criar um novo cliente")
public class CriarClienteDTO {

    @NotBlank(message = "O nome do cliente não pode estar em branco.")
    @Size(min = 3, max = 255, message = "O nome do cliente deve ter entre 3 e 255 caracteres.")
    @Schema(description = "Nome completo do cliente", example = "Maria Oliveira", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @NotBlank(message = "O e-mail do cliente não pode estar em branco.")
    @Email(message = "O e-mail fornecido não é válido.")
    @Size(max = 255, message = "O e-mail não pode exceder 255 caracteres.")
    @Schema(description = "Endereço de e-mail do cliente", example = "maria.oliveira@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Pattern(regexp = "^$|^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$", message = "O telefone deve seguir o formato (XX) XXXXX-XXXX ou (XX) XXXX-XXXX, ou ser vazio.")
    @Schema(description = "Número de telefone do cliente (opcional). Formato esperado: (XX) XXXXX-XXXX ou (XX) XXXX-XXXX", example = "(21) 91234-5678")
    private String telefone;
}