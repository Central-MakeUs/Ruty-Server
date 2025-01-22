package com.ruty.rutyserver.ai;

import com.ruty.rutyserver.ai.dto.GptRequest;
import com.ruty.rutyserver.ai.dto.GptResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Tag(name = "챗지피티 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class GptController {
    @Value("${openai.chat.options.model}")
    private String model;

    @Value("${openai.base-uri}")
    private String apiURL;

    @Autowired
    private RestTemplate template;
    @Operation(
            summary = "프롬포트 요청하기",
            description = "쿼리파라미터로 데이터 요청보냄.")
    @GetMapping("/chat")
    public String chat(@RequestParam(name = "prompt")String prompt){
        GptRequest request = new GptRequest(model, prompt);
        GptResponse gptResponse =  template.postForObject(apiURL, request, GptResponse.class);
        return gptResponse.getChoices().get(0).getMessage().getContent();
    }
}
