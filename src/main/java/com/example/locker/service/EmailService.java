package com.example.locker.service;

import com.example.locker.dto.AluguelDTO;
import com.example.locker.dto.ClienteDTO;

public interface EmailService {
    void enviarEmailBoasVindasCliente(ClienteDTO cliente);
    void enviarEmailAtualizacaoCliente(ClienteDTO cliente);
    void enviarEmailExclusaoCliente(ClienteDTO clienteExcluido);
    void enviarEmailConfirmacaoAluguel(AluguelDTO aluguel);
}