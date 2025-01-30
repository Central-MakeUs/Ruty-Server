package com.ruty.rutyserver.domain.recommend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendRepository extends JpaRepository<RecommendRoutines, Long> {
    List<RecommendRoutines> findAllByMemberId(Long memberId);
}
