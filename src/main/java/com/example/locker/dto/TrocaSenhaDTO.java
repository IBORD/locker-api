package com.example.locker.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TrocaSenhaDTO {
    @NotBlank
    private String senhaAtual;
    @NotBlank
    private String novaSenha;
}