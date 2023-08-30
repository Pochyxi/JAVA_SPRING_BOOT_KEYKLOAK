package com.developez.repository;

import com.developez.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    List<Card> findCardsByTeams_IdAndTeams_AccountsOwner_AccountEmail(Integer id, String email);

    void deleteCardByIdAndTeams_AccountsOwner_AccountEmail( Integer cardId, String email);
}
