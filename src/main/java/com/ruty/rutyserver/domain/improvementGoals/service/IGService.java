package com.ruty.rutyserver.domain.improvementGoals.service;

import com.ruty.rutyserver.domain.improvementGoals.dto.ImprovementGoalReq;
import com.ruty.rutyserver.domain.improvementGoals.repository.IGRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IGService {
    private final IGRepository igRepository;

    public List<ImprovementGoalReq> getAllGoals() {
        return igRepository.findAll()
                .stream()
                .map(ImprovementGoalReq::of)
                .toList();
    }
}
