package com.ruty.rutyserver.domain.routine;

import com.ruty.rutyserver.domain.recommend.RecommendRoutines;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routines, Long> {
    List<Routines> findAllByMemberId(Long memberId);
}
