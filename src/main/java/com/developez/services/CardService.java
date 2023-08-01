package com.developez.services;

import com.developez.model.Card;
import com.developez.model.SkillsStatistics;
import com.developez.model.Teams;
import com.developez.repository.CardRepository;
import com.developez.repository.SkillsStatisticsRepository;
import com.developez.repository.TeamsRepository;
import com.developez.requestModels.NewCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final TeamsRepository teamsRepository;

    private final SkillsStatisticsRepository skillsStatisticsRepository;

    @Autowired
    public CardService( CardRepository cardRepository, TeamsRepository teamsRepository, SkillsStatisticsRepository skillsStatisticsRepository ) {
        this.cardRepository = cardRepository;
        this.teamsRepository = teamsRepository;
        this.skillsStatisticsRepository = skillsStatisticsRepository;
    }

    public ResponseEntity<?> saveCardDetails(NewCardRequest card ) {

        Optional<Teams> teams = teamsRepository.findById( card.getTeamsId() );

        SkillsStatistics skillsStatistics = SkillsStatistics.builder()
                .velocity( 50 )
                .shoot( 50 )
                .pass( 50 )
                .dribbling( 50 )
                .defence( 50 )
                .physical( 50 )
                .build();

        SkillsStatistics skillSaved = skillsStatisticsRepository.save( skillsStatistics );

        if( teams.isPresent() ) {
            Card newCard = new Card();
            newCard.setName( card.getName() );
            newCard.setSurname( card.getSurname() );
            newCard.setTeams( teams.get() );
            newCard.setSkillsStatistics( skillSaved );

            Card cardSaved = cardRepository.save( newCard );

            return ResponseEntity.ok( cardSaved );
        } else {
            return new ResponseEntity<>( "Team non trovato", HttpStatus.NOT_FOUND);
        }

    }
}
