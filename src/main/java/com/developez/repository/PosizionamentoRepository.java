package com.developez.repository;

import com.developez.model.Posizionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosizionamentoRepository extends JpaRepository<Posizionamento, Integer> {
}
