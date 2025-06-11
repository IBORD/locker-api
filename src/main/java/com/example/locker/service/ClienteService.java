package com.example.locker.service;

import com.example.locker.dto.ClienteDTO;
import com.example.locker.dto.CriarClienteDTO;
import com.example.locker.dto.AtualizarClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ClienteService {
    ClienteDTO criarCliente(CriarClienteDTO criarClienteDTO);
    ClienteDTO buscarClientePorId(Long id);
    ClienteDTO buscarClientePorEmail(String email);
    Page<ClienteDTO> buscarTodosClientes(Pageable pageable);
    ClienteDTO atualizarCliente(Long id, AtualizarClienteDTO atualizarClienteDTO);
    void deletarCliente(Long id);
}