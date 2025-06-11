package com.example.locker.repository;

import com.example.locker.entity.Armario;
import com.example.locker.enums.StatusArmario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArmarioRepository extends JpaRepository<Armario, Long> {

    Page<Armario> findByLocalizacaoId(Long localizacaoId, Pageable pageable);

    Optional<Armario> findByNumeroDoArmarioAndLocalizacaoId(String numeroArmario, Long localizacaoId);

    @Modifying
    @Query("UPDATE Armario a SET a.status = :status WHERE a.id = :id")
    int atualizarStatus(Long id, StatusArmario status);
}