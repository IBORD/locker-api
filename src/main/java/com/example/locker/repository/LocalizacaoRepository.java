package com.example.locker.repository;

import com.example.locker.dto.RelatorioOcupacaoLocalizacaoDTO;
import com.example.locker.entity.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {


    @Query("""
            SELECT new com.example.locker.dto.RelatorioOcupacaoLocalizacaoDTO(
                l.id,
                l.nome,
                COUNT(ar.id),
                SUM(CASE WHEN ar.status = 'DISPONIVEL' THEN 1 ELSE 0 END),
                SUM(CASE WHEN ar.status = 'OCUPADO' THEN 1 ELSE 0 END),
                SUM(CASE WHEN ar.status = 'MANUTENCAO' THEN 1 ELSE 0 END),
                SUM(CASE WHEN ar.status = 'FORA_DE_SERVICO' THEN 1 ELSE 0 END)
            )
            FROM Localizacao l
            LEFT JOIN l.armarios ar
            GROUP BY l.id, l.nome
            ORDER BY l.nome
            """)
    List<RelatorioOcupacaoLocalizacaoDTO> gerarRelatorioOcupacao();
}