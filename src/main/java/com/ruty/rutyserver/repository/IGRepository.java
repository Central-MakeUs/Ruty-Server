package com.ruty.rutyserver.repository;

import com.ruty.rutyserver.entity.ImprovementGoals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGRepository extends JpaRepository<ImprovementGoals, Long> {
}
