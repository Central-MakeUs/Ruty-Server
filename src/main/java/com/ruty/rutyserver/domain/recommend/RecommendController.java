package com.ruty.rutyserver.domain.recommend;

import com.ruty.rutyserver.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Tag(name = "챗지피티 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {
    private final RecommendService recommendService;
    @Operation(
            summary = "프롬포트 요청하기(인증토큰 필요)",
            description = "파라미터에 개선하고 싶은 점을 반점으로 구분해서 넣어주면 됨.<br>" +
                    "ex: /api/recommend/gpt?안정적인 주거환경 만들기, 외로움과 고립감 해소하기")
    @GetMapping("/gpt")
    public ResponseEntity<?> chat(@RequestParam(name = "prompt") String prompt,
                                  Principal principal){
        String response = recommendService.getGptResponse(prompt, principal.getName());
        return ResponseEntity.ok(ApiResponse.created(response));
    }
}
