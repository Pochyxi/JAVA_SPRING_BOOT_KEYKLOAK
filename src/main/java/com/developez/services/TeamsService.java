package com.developez.services;

import com.developez.model.Accounts;
import com.developez.model.Teams;
import com.developez.repository.AccountsRepository;
import com.developez.repository.TeamsRepository;
import com.developez.requestModels.POST.POSTTeamRequest;
import com.developez.requestModels.PUT.PUTTeamsRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Teams> GET_Teams(String email ) {
        List<Teams> teams = teamsRepository.findTeamsByAccountsOwner_AccountEmail( email );

        if( teams.isEmpty() ) {
            return null;
        } else {
            return teams;
        }
    }

    public Teams GET_SINGLE_Teams( Integer id, String email ) {
        Optional<Teams> teams = teamsRepository.findTeamsByIdAndAccountsOwner_AccountEmail( id, email );

        return teams.orElse( null );
    }

    public Teams POST_Teams( POSTTeamRequest teams ) {
        Optional<Accounts> accounts = accountsRepository.findAccountsByAccountEmail( teams.getOwnerEmail() );

        if ( accounts.isPresent() ) {
            Teams newTeam = Teams.builder()
                    .accountsOwner( accounts.get() )
                    .teamName( teams.getTeamName() )
                    .build();

            return teamsRepository.save( newTeam );
        } else {
            return null;
        }
    }

    public Teams PUT_Teams( PUTTeamsRequest teams ) {
        Optional<Teams> teamsOptional = teamsRepository.findById( teams.getTeamsId() );

        if( teamsOptional.isPresent() ) {
            Teams teamsModified = Teams.builder()
                    .id( teamsOptional.get().getId() )
                    .teamName( teams.getTeamName() == null ? teamsOptional.get().getTeamName() : teams.getTeamName() )
                    .accountsOwner( teamsOptional.get().getAccountsOwner() )
                    .cards( teamsOptional.get().getCards() )
                    .build();

            return teamsRepository.save( teamsModified );
        } else {
            return null;
        }
    }

    @Transactional
    public Teams DELETE_Teams( Integer id, String email ) {

        Teams teams = teamsRepository.findTeamsByAccountsOwner_AccountEmail( email ).stream()
                .filter( team -> team.getId().equals( id ) )
                .findFirst()
                .orElse( null );



        if( teams != null ) {
            teamsRepository.delete( teams );
            return teams;
        } else {
            return null;
        }
    }
}
