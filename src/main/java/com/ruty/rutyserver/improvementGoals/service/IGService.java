package com.ruty.rutyserver.improvementGoals.service;

import com.ruty.rutyserver.improvementGoals.dto.ImprovementGoalReq;
import com.ruty.rutyserver.improvementGoals.repository.IGRepository;
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
