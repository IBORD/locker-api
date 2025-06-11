package com.example.locker.service;

import com.example.locker.dto.AluguelDTO;
import com.example.locker.dto.CriarAluguelDTO;
import java.util.List;

public interface AluguelService {
    AluguelDTO criarAluguel(CriarAluguelDTO criarAluguelDTO);
    AluguelDTO finalizarAluguel(Long aluguelId);
    AluguelDTO buscarAluguelPorId(Long id);
    List<AluguelDTO> buscarAlugueisPorCliente(Long clienteId);
}