package com.fiap.ford_vinshare.api.repository;

import com.fiap.ford_vinshare.api.model.LeadRetencao;
import com.fiap.ford_vinshare.api.model.StatusLead;
import com.fiap.ford_vinshare.api.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LeadRetencaoRepository extends JpaRepository<LeadRetencao, Long> {
    List<LeadRetencao> findByStatus(StatusLead status);

    boolean existsByVeiculoAndStatusIn(Veiculo veiculo, Collection<StatusLead> statuses);
}
