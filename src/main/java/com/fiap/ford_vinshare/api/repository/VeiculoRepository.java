package com.fiap.ford_vinshare.api.repository;

import com.fiap.ford_vinshare.api.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    boolean existsByVinIgnoreCase(String vin);

    Optional<Veiculo> findByVinIgnoreCase(String vin);

    List<Veiculo> findByModeloContainingIgnoreCase(String modelo);
}
