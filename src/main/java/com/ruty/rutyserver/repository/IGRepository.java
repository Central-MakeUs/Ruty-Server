package com.ruty.rutyserver.repository;

import com.ruty.rutyserver.entity.ImprovementGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGRepository extends JpaRepository<ImprovementGoal, Long> {
}
