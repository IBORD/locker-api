package com.example.locker.entity;

import com.example.locker.enums.StatusAluguel;
import jakarta.persistence.*; // Import correto
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ALUGUEIS")
public class Aluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "armario_id", referencedColumnName = "id")
    private Armario armario;

    @Column(name = "data_hora_inicio")
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private LocalDateTime dataHoraFim;

    @Column(name = "codigo_acesso", unique = true)
    private String codigoAcesso;

    @Enumerated(EnumType.STRING)
    private StatusAluguel status;

    private BigDecimal preco;

    @Column(name = "data_hora_pagamento")
    private LocalDateTime dataHoraPagamento;

    @Column(name = "detalhes_pagamento")
    private String detalhesPagamento;
}