package com.developez.services;

import com.developez.model.SkillsStatistics;
import com.developez.repository.SkillsStatisticsRepository;
import com.developez.requestModels.PUT.PUTSkillsStatistic;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillsStatisticsService {

    private final SkillsStatisticsRepository skillsStatisticsRepository;

    @Autowired
    public SkillsStatisticsService( SkillsStatisticsRepository skillsStatisticsRepository ) {
        this.skillsStatisticsRepository = skillsStatisticsRepository;
    }

    public SkillsStatistics GET_SkillsStatistics( Integer id, String email ) {
        return skillsStatisticsRepository.findSkillsStatisticsByIdAndCard_Teams_AccountsOwner_AccountEmail( id, email ).orElse( null );
    }

    public SkillsStatistics PUT_SkillStatistics( PUTSkillsStatistic skillsStatistic ) {
        SkillsStatistics updatedSkillsStatistic = skillsStatisticsRepository.findById( skillsStatistic.getId() ).orElse( null );

        if( updatedSkillsStatistic != null ) {
            updatedSkillsStatistic.setVelocity( skillsStatistic.getVelocity() == null ? updatedSkillsStatistic.getVelocity() : skillsStatistic.getVelocity() );
            updatedSkillsStatistic.setShoot( skillsStatistic.getShoot() == null ? updatedSkillsStatistic.getShoot() : skillsStatistic.getShoot() );
            updatedSkillsStatistic.setPass( skillsStatistic.getPass() == null ? updatedSkillsStatistic.getPass() : skillsStatistic.getPass() );
            updatedSkillsStatistic.setDribbling( skillsStatistic.getDribbling() == null ? updatedSkillsStatistic.getDribbling() : skillsStatistic.getDribbling() );
            updatedSkillsStatistic.setDefence( skillsStatistic.getDefence() == null ? updatedSkillsStatistic.getDefence() : skillsStatistic.getDefence() );
            updatedSkillsStatistic.setPhysical( skillsStatistic.getPhysical() == null ? updatedSkillsStatistic.getPhysical() : skillsStatistic.getPhysical() );
            updatedSkillsStatistic.setCard( updatedSkillsStatistic.getCard() );

            return skillsStatisticsRepository.save( updatedSkillsStatistic );
        } else {
            return null;
        }
    }

    @Transactional
    public SkillsStatistics DELETE_SkillsStatistics( Integer id, String email) {
        Optional<SkillsStatistics> skillsStatistics = skillsStatisticsRepository.findById( id );

        if( skillsStatistics.isPresent() ) {
            skillsStatisticsRepository.deleteSkillsStatisticsByIdAndCard_Teams_AccountsOwner_AccountEmail( id, email );
            return skillsStatistics.get();
        } else {
            return null;
        }
    }
}
