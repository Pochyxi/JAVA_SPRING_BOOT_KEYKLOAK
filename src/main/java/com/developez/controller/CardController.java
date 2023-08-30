package com.developez.controller;

import com.developez.model.Card;
import com.developez.requestModels.POST.POSTCardRequest;
import com.developez.requestModels.PUT.PUTCardRequest;
import com.developez.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/card" )
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController( CardService cardService ) {
        this.cardService = cardService;
    }


    @GetMapping
    @PreAuthorize( "#email == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')" )
    public ResponseEntity<?> GET_Cards( @RequestParam String email,  @RequestParam Integer id ) {
        List<Card> card = cardService.GET_Card( email, id );

        return new ResponseEntity<>( card, HttpStatus.OK );
    }

    @PostMapping
    @PreAuthorize( "#card.accountEmail == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')" )
    public ResponseEntity<?> saveCardDetails( @RequestBody POSTCardRequest card ) {
        Card newCard = cardService.POST_Card( card );

        if( newCard != null ) {
            return new ResponseEntity<>( newCard, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Team non trovato", HttpStatus.BAD_REQUEST );
        }
    }

    @PutMapping
    @PreAuthorize( "#card.accountEmail == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')" )
    public ResponseEntity<?> PUT_Card( @RequestBody PUTCardRequest card ) {
        Card cardModified = cardService.PUT_Card( card );

        if( cardModified != null ) {
            return new ResponseEntity<>( cardModified, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Richiesta rifiutata", HttpStatus.BAD_REQUEST );
        }
    }

    @DeleteMapping
    @PreAuthorize( "#email == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')" )
    public ResponseEntity<?> DELETE_Card( @RequestParam Integer id, String email ) {
        Card card = cardService.DELETE_Card( id, email );

        if( card != null ) {
            return new ResponseEntity<>( card, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Carta non trovata", HttpStatus.NOT_FOUND );
        }
    }

}
