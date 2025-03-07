package com.ruty.rutyserver.repository;

import com.ruty.rutyserver.entity.RoutineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoutineHistoryRepository extends JpaRepository<RoutineHistory, Long> {
    @Query("SELECT rh FROM RoutineHistory rh WHERE rh.routine.id = :routineId AND YEAR(rh.date) = :year AND MONTH(rh.date) = :month")
    List<RoutineHistory> findAllByRoutineIdAndDate(@Param("routineId") Long routineId, @Param("year") Long year, @Param("month") Long month);

    List<RoutineHistory> findAllByRoutineId(Long routineId);
}
