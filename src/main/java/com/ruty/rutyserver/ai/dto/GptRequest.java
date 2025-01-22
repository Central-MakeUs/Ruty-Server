package com.ruty.rutyserver.ai.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GptRequest {
    private String model;
    private List<Message> messages;

    public GptRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", "넌 AI 어드바이저야. 혼자 사는 사람들에게 유익한 생활습관을 추천하는 것이 너의 역할이야."));
        this.messages.add(new Message("user", prompt));
    }
}
