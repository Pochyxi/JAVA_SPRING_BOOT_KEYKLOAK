package com.developez.controller;

import com.developez.model.SkillsStatistics;
import com.developez.requestModels.PUT.PUTSkillsStatistic;
import com.developez.services.SkillsStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/v1/skillsStatistics" )
public class SkillsStatisticsController {

    private final SkillsStatisticsService skillsStatisticsService;

    @Autowired
    public SkillsStatisticsController( SkillsStatisticsService skillsStatisticsService ) {
        this.skillsStatisticsService = skillsStatisticsService;
    }

    @GetMapping
    @PreAuthorize( "#email == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')" )
    public ResponseEntity<?> GET_SkillsStatistics(@RequestParam Integer id, @RequestParam String email ) {
        SkillsStatistics getStatistic = skillsStatisticsService.GET_SkillsStatistics( id, email );

        if( getStatistic != null ) {
            return new ResponseEntity<>( getStatistic, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Statistiche non trovate", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    @PreAuthorize( "#skillsStatistics.controlEmail == authentication.principal.claims.get('preferred_username') or hasRole" +
            "('ADMIN')" )
    public ResponseEntity<?> PUT_SkillsStatistics( @RequestBody PUTSkillsStatistic skillsStatistics ) {
        SkillsStatistics putSkillsStatistics = skillsStatisticsService.PUT_SkillStatistics( skillsStatistics );

        if( putSkillsStatistics != null ) {
            return new ResponseEntity<>( putSkillsStatistics, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Richiesta rifiutata", HttpStatus.BAD_REQUEST );
        }

    }

    @DeleteMapping
    @PreAuthorize( "#email == authentication.principal.claims.get('preferred_username') or hasRole" +
            "('ADMIN')" )
    public ResponseEntity<?> DELETE_SkillsStatistics( @RequestParam Integer id, @RequestParam String email ) {
        SkillsStatistics deleteSkillsStatistics = skillsStatisticsService.DELETE_SkillsStatistics( id, email );

        if( deleteSkillsStatistics != null ) {
            return new ResponseEntity<>( deleteSkillsStatistics, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Richiesta rifiutata", HttpStatus.BAD_REQUEST );
        }
    }


}
