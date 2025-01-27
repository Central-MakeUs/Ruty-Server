package com.ruty.rutyserver.domain.recommend;

import com.ruty.rutyserver.domain.member.entity.Member;
import com.ruty.rutyserver.domain.member.exception.MemberNotFoundException;
import com.ruty.rutyserver.domain.member.repository.MemberRepository;
import com.ruty.rutyserver.domain.recommend.dto.GptRequest;
import com.ruty.rutyserver.domain.recommend.dto.GptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecommendService {
    @Value("${openai.chat.options.model}")
    private String model;

    @Value("${openai.base-uri}")
    private String apiURL;

    //@ Authowired
    private final RestTemplate template;

    private final RecommendRepository recommendRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String getGptResponse(String prompt, String email) {
        GptRequest request = new GptRequest(model, prompt);
        GptResponse gptResponse =  template.postForObject(apiURL, request, GptResponse.class);

        // 1. 사용자 정보 조회하기
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        // 2. 사용자와 연관지어서 gpt 응답값 저장하기
        return gptResponse.getChoices().get(0).getMessage().getContent();
    }
}
