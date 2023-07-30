package com.developez.controller;

import com.developez.requestModels.NewCardRequest;
import com.developez.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController( CardService cardService ) {
        this.cardService = cardService;
    }

    @PostMapping( "/newCard" )
    public ResponseEntity<?> saveCardDetails( @RequestBody NewCardRequest card ) {
        return new ResponseEntity<>( cardService.saveCardDetails(card), HttpStatus.OK );
    }

}
