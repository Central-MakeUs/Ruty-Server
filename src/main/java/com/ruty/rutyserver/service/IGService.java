package com.ruty.rutyserver.service;

import com.ruty.rutyserver.dto.improvement_goals.ImprovementGoalReq;
import com.ruty.rutyserver.repository.IGRepository;
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
