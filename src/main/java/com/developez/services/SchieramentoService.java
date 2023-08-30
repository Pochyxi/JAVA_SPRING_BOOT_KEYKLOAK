package com.developez.services;

import com.developez.model.Schieramento;
import com.developez.model.Teams;
import com.developez.repository.SchieramentoRepository;
import com.developez.repository.TeamsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SchieramentoService {

    private final SchieramentoRepository schieramentoRepository;
    private final TeamsRepository teamsRepository;

    @Autowired
    public SchieramentoService(SchieramentoRepository schieramentoRepository, TeamsRepository teamsRepository) {
        this.schieramentoRepository = schieramentoRepository;
        this.teamsRepository = teamsRepository;
    }

    public Schieramento findById(Integer id) {
        return schieramentoRepository.findById(id).orElse(null);
    }

    public List<Schieramento> findByTeamIdAndTeamAccountEmail( Integer teamId, String email) {
        return schieramentoRepository.findSchieramentosByTeam_IdAndTeam_AccountsOwner_AccountEmail(teamId, email);
    }

    // todo: Nel momento in cui salviamo un nuovo schieramento, specificando l'id del team, dobbiamo ricercare tutte le
    //  card che appartengono al team e creare un posizionamento per ognuna di esse. Di default il ruolo sarà
    //  "PANCHINARO"
    public Schieramento save(Integer teamId) {
        Optional<Teams> teamsFound = teamsRepository.findById(teamId);
        if (teamsFound.isPresent()) {
            Schieramento schieramento = Schieramento
                    .builder()
                    .team(teamsFound.get())
                    .posizionamenti( new ArrayList<>() )
                    .build();
            return schieramentoRepository.save(schieramento);
        }
        return null;
    }


    @Transactional
    public Schieramento delete(Integer schieramentoId) {
        Schieramento schieramento = schieramentoRepository.findById(schieramentoId).orElse(null);
        if (schieramento != null) {
            schieramentoRepository.delete(schieramento);
            return schieramento;
        }
        return null;
    }
}
