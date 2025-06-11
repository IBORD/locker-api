package com.example.locker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para atualizar um cliente existente")
public class AtualizarClienteDTO {

    @Size(min = 3, max = 255, message = "O nome do cliente deve ter entre 3 e 255 caracteres, se fornecido.")
    @Schema(description = "Novo nome completo do cliente (opcional)", example = "Maria Oliveira Santos")
    private String nome;

    @Email(message = "O e-mail fornecido não é válido, se fornecido.")
    @Size(max = 255, message = "O e-mail não pode exceder 255 caracteres, se fornecido.")
    @Schema(description = "Novo endereço de e-mail do cliente (opcional)", example = "maria.santos@example.com")
    private String email;

    @Pattern(regexp = "^$|^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$", message = "O telefone deve seguir o forma2to (XX) XXXXX-XXXX ou (XX) XXXX-XXXX, ou ser vazio/nulo se fornecido.")
    @Schema(description = "Novo número de telefone do cliente (opcional)", example = "(21) 99999-8888")
    private String telefone;
}