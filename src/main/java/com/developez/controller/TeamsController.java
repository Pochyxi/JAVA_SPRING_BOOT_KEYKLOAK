package com.developez.controller;


import com.developez.model.Teams;
import com.developez.requestModels.POST.POSTTeamRequest;
import com.developez.requestModels.PUT.PUTTeamsRequest;
import com.developez.services.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamsController {

    private final TeamsService teamsService;

    @Autowired
    public TeamsController( TeamsService teamsService ) {
        this.teamsService = teamsService;
    }


    @GetMapping("/{id}")
    @PreAuthorize("#email == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')")
    public ResponseEntity<?> getSingleTeam(@PathVariable Integer id, @RequestParam String email) {
        Teams team = teamsService.GET_SINGLE_Teams( id, email );

        if( team != null ) {
            return new ResponseEntity<>( team, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Nessun team trovato con questa email", HttpStatus.NOT_FOUND );
        }
    }

    @GetMapping
    @PreAuthorize("#ownerEmail == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')")
    public ResponseEntity<?> getTeams(@RequestParam String ownerEmail) {
        List<Teams> teams = teamsService.GET_Teams( ownerEmail );

        if( teams != null ) {
            return new ResponseEntity<>( teams, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Nessun team trovato con questa email", HttpStatus.NOT_FOUND );
        }
    }

    @PostMapping
    @PreAuthorize("#teams.ownerEmail == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')")
    public ResponseEntity<?> postTeams( @RequestBody POSTTeamRequest teams ) {
        Teams newTeam = teamsService.POST_Teams( teams );

        if( newTeam != null ) {
            return new ResponseEntity<>( newTeam, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Team già esistente per questa email", HttpStatus.BAD_REQUEST );
        }
    }

    @PutMapping
    @PreAuthorize("#teams.ownerEmail == authentication.principal.claims.get('preferred_username') or hasRole" +
            "('ADMIN')")
    public ResponseEntity<?> putTeams( @RequestBody PUTTeamsRequest teams ) {

        Teams teamsModified = teamsService.PUT_Teams( teams );

        if( teamsModified == null ) {
            return new ResponseEntity<>( "Nessun team trovato con questa email", HttpStatus.NOT_FOUND );
        } else {
            return new ResponseEntity<>( teamsModified, HttpStatus.OK );
        }
    }

    @DeleteMapping
    @PreAuthorize("#ownerEmail == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteTeams( @RequestParam Integer id, @RequestParam String ownerEmail ) {
        Teams teamsDeleted = teamsService.DELETE_Teams( id, ownerEmail );

        if( teamsDeleted == null ) {
            return new ResponseEntity<>( "Nessun team trovato con questa email", HttpStatus.NOT_FOUND );
        } else {
            return new ResponseEntity<>( teamsDeleted, HttpStatus.OK );
        }
    }
}
