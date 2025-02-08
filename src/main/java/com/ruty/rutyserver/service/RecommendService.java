package com.ruty.rutyserver.service;

import com.ruty.rutyserver.dto.recommend.GptRequest;
import com.ruty.rutyserver.dto.recommend.GptResponse;
import com.ruty.rutyserver.dto.recommend.RecommendRoutineDto;
import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.RecommendRoutine;
import com.ruty.rutyserver.exception.MemberNotFoundException;
import com.ruty.rutyserver.exception.RecommendNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public interface RecommendService {
    List<RecommendRoutineDto> getMyAllRecommends(String email);

    RecommendRoutineDto getMyRecommend(String email, Long recommendId);

    List<RecommendRoutineDto> getAllRecommends();

    List<RecommendRoutineDto> getGptResponse(String prompt, String email);
}
