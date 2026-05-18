package com.fiap.ford_vinshare.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"veiculo", "concessionaria"})
@Entity
@Table(name = "ordens_servico")
public class OrdemServico {

    @Id
    @SequenceGenerator(name = "ordem_servico_seq", sequenceName = "SEQ_ORDENS_SERVICO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordem_servico_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concessionaria_id", nullable = false)
    private Concessionaria concessionaria;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servico", nullable = false, length = 40)
    private TipoServico tipoServico;

    @Column(name = "data_servico", nullable = false)
    private LocalDate dataServico;

    @Column(nullable = false)
    private Integer quilometragem;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusServico status;

    @Column(length = 255)
    private String observacao;
}
