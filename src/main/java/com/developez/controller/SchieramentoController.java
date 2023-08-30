package com.developez.controller;

import com.developez.model.Schieramento;
import com.developez.services.SchieramentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/schieramento")
public class SchieramentoController {

    private final SchieramentoService schieramentoService;

    @Autowired
    public SchieramentoController( SchieramentoService schieramentoService ) {
        this.schieramentoService = schieramentoService;
    }

    @GetMapping
    @PreAuthorize( "#email == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')" )
    public ResponseEntity<?> GET_Schieramento( @RequestParam Integer id, @RequestParam String email ) {
        Schieramento schieramento = schieramentoService.findById( id );

        if( schieramento != null ) {
            return new ResponseEntity<>( schieramento, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Schieramento non trovato", HttpStatus.BAD_REQUEST );
        }
    }

    @PostMapping
    @PreAuthorize( "#email == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')" )
    public ResponseEntity<?> POST_Schieramento( @RequestParam Integer teamId, @RequestParam String email ) {
        Schieramento newSchieramento = schieramentoService.save( teamId );

        if( newSchieramento != null ) {
            return new ResponseEntity<>( newSchieramento, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Schieramento non trovato", HttpStatus.BAD_REQUEST );
        }
    }

    @DeleteMapping
    @PreAuthorize( "#email == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')" )
    public ResponseEntity<?> DELETE_Schieramento( @RequestParam Integer id, @RequestParam String email ) {
        Schieramento schieramento = schieramentoService.delete( id );

        if( schieramento != null ) {

            return new ResponseEntity<>( "Schieramento eliminato", HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Schieramento non trovato", HttpStatus.BAD_REQUEST );
        }
    }


}
