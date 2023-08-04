package com.developez.services;

import com.developez.model.Card;
import com.developez.model.SkillsStatistics;
import com.developez.model.Teams;
import com.developez.repository.CardRepository;
import com.developez.repository.SkillsStatisticsRepository;
import com.developez.repository.TeamsRepository;
import com.developez.requestModels.POST.POSTCardRequest;
import com.developez.requestModels.PUT.PUTCardRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Card> GET_Card( String email ) {
        List<Card> cards = cardRepository.findCardByTeams_AccountsOwner_AccountEmail(email);

        if( cards.size() > 0 ) {
            return cards;
        } else {
            return null;
        }
    }

    public Card POST_Card( POSTCardRequest card ) {

        Optional<Teams> teams = teamsRepository.findById( card.getTeamsId() );

        if( teams.isPresent() ) {
            SkillsStatistics skillsStatistics = SkillsStatistics.builder()
                    .velocity( 50 )
                    .shoot( 50 )
                    .pass( 50 )
                    .dribbling( 50 )
                    .defence( 50 )
                    .physical( 50 )
                    .build();

            SkillsStatistics skillSaved = skillsStatisticsRepository.save( skillsStatistics );

            Card newCard = Card.builder()
                    .name( card.getName() )
                    .surname( card.getSurname() )
                    .teams( teams.get() )
                    .accountEmail( card.getAccountEmail() == null ? teams.get().getAccountsOwner().getAccountEmail()
                            : card.getAccountEmail() )
                    .skillsStatistics( skillSaved )
                    .build();

            return cardRepository.save( newCard );
        } else {
            return null;
        }
    }

    public Card PUT_Card( PUTCardRequest card ) {
        SkillsStatistics skillsStatistics = skillsStatisticsRepository.findById( card.getSkillStatisticsId() ).orElse( null );

        if( skillsStatistics != null ) {
            Card cardToUpdate = cardRepository.findById( card.getCardId() ).orElse( null );

            if( cardToUpdate != null ) {
                cardToUpdate.setName( card.getName() == null ? cardToUpdate.getName() : card.getName() );
                cardToUpdate.setSurname( card.getSurname() == null ? cardToUpdate.getSurname() : card.getSurname() );
                cardToUpdate.setAccountEmail( card.getAccountEmail() == null ? cardToUpdate.getAccountEmail() : card.getAccountEmail() );
                cardToUpdate.setSkillsStatistics( card.getSkillStatisticsId() == null ? cardToUpdate.getSkillsStatistics() : skillsStatistics );

                return cardRepository.save( cardToUpdate );
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Transactional
    public Card DELETE_Card( Integer cardId, String email ) {
        Card cardToDelete = cardRepository.findById( cardId ).orElse( null );

        if( cardToDelete != null ) {
            cardRepository.deleteCardByIdAndTeams_AccountsOwner_AccountEmail( cardId, email );
            return cardToDelete;
        } else {
            return null;
        }
    }
}
