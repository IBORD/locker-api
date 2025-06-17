package com.example.locker.dto;


import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class UsuarioDTO {
    @Schema(description = "ID do usuário")
    private Integer idUsuario;

    @Schema(description = "Login do usuário")
    private String login;
}