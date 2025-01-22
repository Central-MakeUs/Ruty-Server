package com.ruty.rutyserver.improvementGoals;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
