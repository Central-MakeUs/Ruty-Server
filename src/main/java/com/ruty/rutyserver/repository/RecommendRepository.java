package com.ruty.rutyserver.repository;

import com.ruty.rutyserver.entity.RecommendRoutine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendRepository extends JpaRepository<RecommendRoutine, Long> {
    List<RecommendRoutine> findAllByMemberId(Long memberId);
}
