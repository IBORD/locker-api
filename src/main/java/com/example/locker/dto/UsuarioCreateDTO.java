package com.example.locker.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UsuarioCreateDTO {
    @NotNull
    private String login;
    @NotNull
    private String senha;
    @NotEmpty
    private List<Integer> cargos;
}