package com.developez.repository;

import com.developez.model.SkillsStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsStatisticsRepository extends JpaRepository<SkillsStatistics, Integer> {
}
