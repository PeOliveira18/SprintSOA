package com.fiap.ford_vinshare.api.repository;

import com.fiap.ford_vinshare.api.model.OrdemServico;
import com.fiap.ford_vinshare.api.model.StatusServico;
import com.fiap.ford_vinshare.api.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    List<OrdemServico> findByVeiculoVinIgnoreCaseOrderByDataServicoDesc(String vin);

    List<OrdemServico> findByStatus(StatusServico status);

    Optional<OrdemServico> findTopByVeiculoOrderByDataServicoDesc(Veiculo veiculo);

    boolean existsByVeiculo(Veiculo veiculo);
}
