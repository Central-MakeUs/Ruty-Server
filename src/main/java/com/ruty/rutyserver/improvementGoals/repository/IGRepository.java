package com.ruty.rutyserver.improvementGoals.repository;

import com.ruty.rutyserver.improvementGoals.entity.ImprovementGoals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGRepository extends JpaRepository<ImprovementGoals, Long> {
}
