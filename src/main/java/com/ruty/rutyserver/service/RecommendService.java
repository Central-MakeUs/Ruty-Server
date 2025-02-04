package com.ruty.rutyserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruty.rutyserver.entity.e.Category;
import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.RecommendRoutine;
import com.ruty.rutyserver.exception.MemberNotFoundException;
import com.ruty.rutyserver.repository.MemberRepository;
import com.ruty.rutyserver.dto.recommend.GptRequest;
import com.ruty.rutyserver.dto.recommend.GptResponse;
import com.ruty.rutyserver.dto.recommend.RecommendRoutineDto;
import com.ruty.rutyserver.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendService {
    @Value("${openai.chat.options.model}")
    private String model;

    @Value("${openai.base-uri}")
    private String apiURL;

    //@ Authowired
    private final RestTemplate template;

    private final RecommendRepository recommendRepository;
    private final MemberRepository memberRepository;

    public List<RecommendRoutineDto> getAllRecommend(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<RecommendRoutine> recommendRoutines = recommendRepository.findAllByMemberId(member.getId());
        return recommendRoutines.stream()
                .map(RecommendRoutineDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<RecommendRoutineDto> getGptResponse(String prompt, String email) {
        GptRequest request = new GptRequest(model, prompt);
        GptResponse gptResponse =  template.postForObject(apiURL, request, GptResponse.class);

        // 1. 사용자 정보 조회하기
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        // 2. 사용자와 연관지어서 gpt 응답값 저장하기
        return saveRecommendRoutines(gptResponse, member).stream()
                .map(RecommendRoutineDto::of)
                .collect(Collectors.toList());
    }
    private List<RecommendRoutine> saveRecommendRoutines(GptResponse gptResponse, Member member) {
        // GPT 응답에서 content(JSON 문자열) 추출
        String jsonContent = gptResponse.getChoices().get(0).getMessage().getContent();

        // JSON을 Recommend 엔티티 리스트로 변환
        List<RecommendRoutine> recommendList = parseJsonToRecommend(jsonContent, member);

        // 데이터베이스에 저장
        List<RecommendRoutine> recommendRoutines = recommendRepository.saveAll(recommendList);
        return recommendRoutines;
    }

    private List<RecommendRoutine> parseJsonToRecommend(String jsonContent, Member member) {
        try {
            log.info("parseJson 진입 성공");
            ObjectMapper objectMapper = new ObjectMapper();

            // 1. JSON 문자열이 이스케이프된 경우 정상적인 JSON으로 변환
//            jsonContent = StringEscapeUtils.unescapeJson(jsonContent.trim());

            jsonContent = jsonContent.trim();
            if (jsonContent.startsWith("```") && jsonContent.endsWith("```")) {
                jsonContent = jsonContent.substring(7, jsonContent.length() - 3).trim();
            }

            // 2. JSON 유효성 검사
            if (!jsonContent.startsWith("{") || !jsonContent.endsWith("}")) {
                throw new IllegalArgumentException("Invalid JSON format: " + jsonContent);
            }

            JsonNode rootNode = objectMapper.readTree(jsonContent);
            JsonNode routinesNode = rootNode.get("routines");
            if (routinesNode == null || !routinesNode.isArray()) {
                throw new RuntimeException("Invalid GPT response format");
            }

            // JSON을 엔티티로 변환
            return StreamSupport.stream(routinesNode.spliterator(), false)
                    .map(node -> new RecommendRoutine(
                            node.get("title").asText(),
                            node.get("description").asText(),
                            Category.valueOf(node.get("category").asText()),
                            member
                    ))
                    .collect(Collectors.toList());

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse GPT response", e);
        }
    }
}
