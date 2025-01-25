package com.ruty.rutyserver.common;

import com.ruty.rutyserver.improvementGoals.entity.Category;
import com.ruty.rutyserver.improvementGoals.entity.ImprovementGoals;
import com.ruty.rutyserver.improvementGoals.repository.IGRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Dataloader {

    @Bean
    public CommandLineRunner loadTestData(IGRepository igRepository) {
        return args -> {
            // ImprovementGoals 엔티티를 리스트에 추가
            List<ImprovementGoals> goals = List.of(
                    ImprovementGoals.builder().category(Category.HOUSE).content("안정적인 주거 환경 만들기").build(),
                    ImprovementGoals.builder().category(Category.HOUSE).content("정기적인 집 관리로 편안함 유지하기").build(),
                    ImprovementGoals.builder().category(Category.MONEY).content("합리적이고 계획적인 소비습관 만들기").build(),
                    ImprovementGoals.builder().category(Category.SELFCARE).content("균형잡힌 식습관 꾸준히 유지하기").build(),
                    ImprovementGoals.builder().category(Category.SELFCARE).content("규칙적이고 건강한 생활 리듬 만들기").build(),
                    ImprovementGoals.builder().category(Category.LEISURE).content("외로움과 고립감 해소하기").build(),
                    ImprovementGoals.builder().category(Category.LEISURE).content("스스로를 돌보고 가꿔 더 나은 나 되기").build(),
                    ImprovementGoals.builder().category(Category.LEISURE).content("일과 여가의 조화로 균형있는 삶 살기").build()
            );
            // 리스트를 반복하며 저장
            goals.forEach(igRepository::save);
        };
    }
}
