package com.ruty.rutyserver.repository;

import com.ruty.rutyserver.entity.Routine;
import com.ruty.rutyserver.entity.e.Categories;
import com.ruty.rutyserver.entity.e.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    List<Routine> findAllByMemberId(Long memberId);

    List<Routine> findAllByMemberIdAndCategory(Long memberId, Categories category);

    @Query("SELECT r FROM Routine r JOIN r.weeks w WHERE r.member.id = :memberId AND w = :today")
    List<Routine> findTodayRoutines(@Param("memberId") Long memberId, @Param("today") Week today);

    @Query("SELECT r FROM Routine r WHERE r.startDate <= :date AND r.endDate >= :date")
    List<Routine> findAllByDate(@Param("date") LocalDate localDate);
}
