package com.developez.repository;

import com.developez.model.SkillsStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillsStatisticsRepository extends JpaRepository<SkillsStatistics, Integer> {

    Optional<SkillsStatistics> findSkillsStatisticsByIdAndCard_Teams_AccountsOwner_AccountEmail( Integer id, String email);
    void deleteSkillsStatisticsByIdAndCard_Teams_AccountsOwner_AccountEmail( Integer id, String email);
}
