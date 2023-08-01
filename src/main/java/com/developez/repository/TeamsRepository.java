package com.developez.repository;

import com.developez.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamsRepository extends JpaRepository<Teams, Integer> {

    Optional<Teams> findTeamsByAccountsOwner_AccountEmail(String email);
}
