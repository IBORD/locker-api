package com.example.locker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflitoDeRecursoException extends RuntimeException {
    public ConflitoDeRecursoException(String mensagem) {
        super(mensagem);
    }
}