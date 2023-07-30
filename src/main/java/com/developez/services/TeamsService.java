package com.developez.services;

import com.developez.model.Accounts;
import com.developez.model.Teams;
import com.developez.repository.AccountsRepository;
import com.developez.repository.TeamsRepository;
import com.developez.requestModels.NewTeamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamsService {

    private final TeamsRepository teamsRepository;
    private final AccountsRepository accountsRepository;

    @Autowired
    public TeamsService( TeamsRepository teamsRepository, AccountsRepository accountsRepository ) {
        this.teamsRepository = teamsRepository;
        this.accountsRepository = accountsRepository;
    }

    public Teams saveTeamDetails( NewTeamRequest teams ) {

        Optional<Accounts> accounts = accountsRepository.findAccountsByAccountEmail( teams.getOwnerEmail() );
        Teams newTeams = new Teams();

        if( accounts.isPresent() ) {
            newTeams.setTeamName( teams.getTeamName() );
            newTeams.setAccountsOwner( accounts.get() );
            teamsRepository.save( newTeams );
        } else {
            newTeams = null;
        }

        return newTeams;
    }
}
