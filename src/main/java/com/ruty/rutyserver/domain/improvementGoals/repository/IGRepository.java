package com.ruty.rutyserver.domain.improvementGoals.repository;

import com.ruty.rutyserver.domain.improvementGoals.entity.ImprovementGoals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGRepository extends JpaRepository<ImprovementGoals, Long> {
}
