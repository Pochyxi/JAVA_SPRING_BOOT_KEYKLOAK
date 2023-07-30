package com.developez.controller;


import com.developez.requestModels.NewTeamRequest;
import com.developez.services.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamsController {

    private final TeamsService teamsService;

    @Autowired
    public TeamsController( TeamsService teamsService ) {
        this.teamsService = teamsService;
    }

    @PostMapping("/newTeams")
    public ResponseEntity<?> saveTeamDetails( @RequestBody NewTeamRequest teams ) {
        return new ResponseEntity<>( teamsService.saveTeamDetails(teams), HttpStatus.OK );
    }
}
