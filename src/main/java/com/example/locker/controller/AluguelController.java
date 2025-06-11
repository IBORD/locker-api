package com.example.locker.controller;

import com.example.locker.dto.AluguelDTO;
import com.example.locker.dto.CriarAluguelDTO;
import com.example.locker.service.AluguelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alugueis")
@Tag(name = "Aluguéis", description = "API para gerenciamento de aluguéis de armários")
public class AluguelController {

    private final AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @Operation(summary = "Cria um novo aluguel")
    @PostMapping
    public ResponseEntity<AluguelDTO> criarAluguel(@Valid @RequestBody CriarAluguelDTO criarAluguelDTO) {
        AluguelDTO novoAluguel = aluguelService.criarAluguel(criarAluguelDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoAluguel.getId()).toUri();
        return ResponseEntity.created(location).body(novoAluguel);
    }

    @Operation(summary = "Finaliza um aluguel ativo")
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<AluguelDTO> finalizarAluguel(@PathVariable Long id) {
        AluguelDTO aluguelFinalizado = aluguelService.finalizarAluguel(id);
        return ResponseEntity.ok(aluguelFinalizado);
    }

    @Operation(summary = "Busca um aluguel pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<AluguelDTO> buscarAluguelPorId(@PathVariable Long id) {
        return ResponseEntity.ok(aluguelService.buscarAluguelPorId(id));
    }

    @Operation(summary = "Lista todos os aluguéis de um cliente")
    @GetMapping("/por-cliente/{clienteId}")
    public ResponseEntity<List<AluguelDTO>> buscarAlugueisPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(aluguelService.buscarAlugueisPorCliente(clienteId));
    }
}