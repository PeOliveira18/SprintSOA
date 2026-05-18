package com.fiap.ford_vinshare.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"cliente", "concessionariaVenda"})
@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @SequenceGenerator(name = "veiculo_seq", sequenceName = "SEQ_VEICULOS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "veiculo_seq")
    private Long id;

    @Column(nullable = false, unique = true, length = 17)
    private String vin;

    @Column(nullable = false, length = 40)
    private String marca;

    @Column(nullable = false, length = 80)
    private String modelo;

    @Column(name = "ano_fabricacao", nullable = false)
    private Integer anoFabricacao;

    @Column(name = "data_compra", nullable = false)
    private LocalDate dataCompra;

    @Column(name = "quilometragem_atual", nullable = false)
    private Integer quilometragemAtual;

    @Column(name = "garantia_ativa", nullable = false)
    private Boolean garantiaAtiva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concessionaria_venda_id", nullable = false)
    private Concessionaria concessionariaVenda;
}
