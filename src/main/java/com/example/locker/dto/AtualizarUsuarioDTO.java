package com.example.locker.dto;

import lombok.Data;
import java.util.Set;

@Data
public class AtualizarUsuarioDTO {
    private String login;
    private Set<Integer> cargos;
}