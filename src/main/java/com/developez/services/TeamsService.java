package com.developez.services;

import com.developez.model.Accounts;
import com.developez.model.Teams;
import com.developez.repository.AccountsRepository;
import com.developez.repository.TeamsRepository;
import com.developez.requestModels.NewTeamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> saveTeamDetails(NewTeamRequest teams ) {

        Optional<Accounts> accounts = accountsRepository.findAccountsByAccountEmail( teams.getOwnerEmail() );
        Teams newTeams = new Teams();

        if( accounts.isPresent() ) {
            newTeams.setTeamName( teams.getTeamName() );
            newTeams.setAccountsOwner( accounts.get() );
            return new ResponseEntity<>(teamsRepository.save( newTeams ), HttpStatus.OK);
        } else {
            return new ResponseEntity<>( "Account non trovato", HttpStatus.NOT_FOUND);
        }
    }
}
