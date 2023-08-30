package com.developez.repository;

import com.developez.model.Schieramento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchieramentoRepository extends JpaRepository<Schieramento, Integer> {

    List<Schieramento> findSchieramentosByTeam_IdAndTeam_AccountsOwner_AccountEmail(Integer teamId, String email);
}
