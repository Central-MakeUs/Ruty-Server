package com.ruty.rutyserver.repository;

import com.ruty.rutyserver.entity.CategoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryLevelRepository extends JpaRepository<CategoryLevel, Long> {
    List<CategoryLevel> findAllByMemberId(Long memberId);
}
