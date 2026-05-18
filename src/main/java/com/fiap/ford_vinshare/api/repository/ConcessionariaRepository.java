package com.fiap.ford_vinshare.api.repository;

import com.fiap.ford_vinshare.api.model.Concessionaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcessionariaRepository extends JpaRepository<Concessionaria, Long> {
    boolean existsByCodigoIgnoreCase(String codigo);

    List<Concessionaria> findByAtivoTrue();
}
