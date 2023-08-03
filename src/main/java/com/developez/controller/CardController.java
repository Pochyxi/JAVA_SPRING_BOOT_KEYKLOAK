package com.developez.controller;

import com.developez.requestModels.NewCardRequest;
import com.developez.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/v1/card" )
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController( CardService cardService ) {
        this.cardService = cardService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> saveCardDetails( @RequestBody NewCardRequest card ) {
        return cardService.saveCardDetails( card );
    }

}