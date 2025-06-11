package com.example.locker.entity;

import com.example.locker.enums.TamanhoArmario;
import com.example.locker.enums.StatusArmario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ARMARIOS")
public class Armario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_armario")
    private String numeroDoArmario;

    @Enumerated(EnumType.STRING)
    private TamanhoArmario tamanho;

    @Enumerated(EnumType.STRING)
    private StatusArmario status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localizacao_id", referencedColumnName = "id")
    @JsonBackReference
    private Localizacao localizacao;

    private String observacoes;
}