package com.developez.repository;

import com.developez.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamsRepository extends JpaRepository<Teams, Integer> {

    List<Teams> findTeamsByAccountsOwner_AccountEmail( String email);

    void deleteTeamsByIdAndAccountsOwner_AccountEmail( Integer id, String email );
}
