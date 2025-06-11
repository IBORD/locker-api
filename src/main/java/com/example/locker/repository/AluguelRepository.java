package com.example.locker.repository;

import com.example.locker.dto.RelatorioAluguelAtivoDTO;
import com.example.locker.entity.Aluguel;
import com.example.locker.enums.StatusAluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
    List<Aluguel> findByClienteId(Long clienteId);

    // Relatório: Aluguéis Ativos com Detalhes
    @Query("""
            SELECT new com.example.locker.dto.RelatorioAluguelAtivoDTO(
                a.id,
                c.nome,
                c.email,
                ar.numeroDoArmario,
                l.nome,
                a.dataHoraInicio
            )
            FROM Aluguel a
            JOIN a.cliente c
            JOIN a.armario ar
            JOIN ar.localizacao l
            WHERE a.status = :status
            ORDER BY l.nome, a.dataHoraInicio DESC
            """)
    List<RelatorioAluguelAtivoDTO> findAlugueisAtivosComDetalhes(StatusAluguel status);
}