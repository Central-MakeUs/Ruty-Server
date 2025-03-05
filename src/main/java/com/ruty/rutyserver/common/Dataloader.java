package com.ruty.rutyserver.common;

import com.ruty.rutyserver.entity.CategoryLevel;
import com.ruty.rutyserver.entity.Routine;
import com.ruty.rutyserver.entity.e.Categories;
import com.ruty.rutyserver.entity.ImprovementGoal;
import com.ruty.rutyserver.entity.e.SocialType;
import com.ruty.rutyserver.entity.e.Week;
import com.ruty.rutyserver.repository.IGRepository;
import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.e.MemberRole;
import com.ruty.rutyserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Dataloader {

    @Bean
    public CommandLineRunner loadTestData(IGRepository igRepository,
                                          MemberRepository memberRepository) {
        return args -> {
            // ImprovementGoals 엔티티를 리스트에 추가
            List<ImprovementGoal> goals = List.of(
                    ImprovementGoal.builder().category(Categories.HOUSE).content("안정적인 주거 환경 만들기").build(),
                    ImprovementGoal.builder().category(Categories.HOUSE).content("정기적인 집 관리로 편안함 유지하기").build(),
                    ImprovementGoal.builder().category(Categories.MONEY).content("합리적이고 계획적인 소비습관 만들기").build(),
                    ImprovementGoal.builder().category(Categories.SELFCARE).content("균형잡힌 식습관 꾸준히 유지하기").build(),
                    ImprovementGoal.builder().category(Categories.SELFCARE).content("규칙적이고 건강한 생활 리듬 만들기").build(),
                    ImprovementGoal.builder().category(Categories.LEISURE).content("외로움과 고립감 해소하기").build(),
                    ImprovementGoal.builder().category(Categories.LEISURE).content("스스로를 돌보고 가꿔 더 나은 나 되기").build(),
                    ImprovementGoal.builder().category(Categories.LEISURE).content("일과 여가의 조화로 균형있는 삶 살기").build()
            );
            // 리스트를 반복하며 저장
            goals.forEach(igRepository::save);

            Member member = Member.builder()
                    .email("plmko0914@gmail.com")
                    .nickName("song")
                    .name("송성훈")
                    .picture(null)
                    .socialType(SocialType.GOOGLE)
                    .role(MemberRole.ROLE_MEMBER)
                    .isAgree(true)
                    .refreshToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSZWZyZXNoVG9rZW4iLCJpYXQiOjE3MzgyNDgxMzksImV4cCI6MTczOTQ1NzczOX0.UOnjMkHLblOjvkJhekknanHv90W06K6LKiexNtQtD1c")
                    .build();


            List<CategoryLevel> levels = List.of(
                    CategoryLevel.builder().category(Categories.HOUSE).member(member).build(),
                    CategoryLevel.builder().category(Categories.LEISURE).member(member).build(),
                    CategoryLevel.builder().category(Categories.SELFCARE).member(member).build(),
                    CategoryLevel.builder().category(Categories.MONEY).member(member).build()
            );

            List<Routine> routines = List.of(
                    Routine.builder()
                            .title("아침 스트레칭")
                            .description("하루를 활기차게 시작하기 위해 간단한 스트레칭을 한다.")
                            .weeks(List.of(Week.MON, Week.WED, Week.FRI))
                            .startDate(LocalDate.now().minusMonths(1))
                            .endDate(LocalDate.now().plusMonths(1))
                            .category(Categories.SELFCARE)
                            .member(member)
                            .build(),

                    Routine.builder()
                            .title("저녁 10분 명상")
                            .description("하루를 마무리하면서 명상으로 마음을 정리한다.")
                            .weeks(List.of(Week.TUE, Week.THU, Week.SAT))
                            .startDate(LocalDate.now().minusMonths(2))
                            .endDate(LocalDate.now().plusMonths(3))
                            .category(Categories.LEISURE)
                            .member(member)
                            .build(),

                    Routine.builder()
                            .title("주간 가계부 정리")
                            .description("한 주 동안 사용한 비용을 정리하고 예산을 점검한다.")
                            .weeks(List.of(Week.SUN))
                            .startDate(LocalDate.now().minusMonths(1))
                            .endDate(LocalDate.now().plusMonths(2))
                            .category(Categories.MONEY)
                            .member(member)
                            .build(),

                    Routine.builder()
                            .title("매일 아침 물 2컵 마시기")
                            .description("수분 보충을 위해 아침마다 물을 두 컵 마신다.")
                            .weeks(List.of(Week.MON, Week.TUE, Week.WED, Week.THU, Week.FRI, Week.SAT, Week.SUN))
                            .startDate(LocalDate.now().minusMonths(3))
                            .endDate(LocalDate.now().plusMonths(3))
                            .category(Categories.SELFCARE)
                            .member(member)
                            .build(),

                    Routine.builder()
                            .title("집 정리하기")
                            .description("매주 한 번씩 집을 깨끗하게 정리한다.")
                            .weeks(List.of(Week.SAT))
                            .startDate(LocalDate.now().minusMonths(1))
                            .endDate(LocalDate.now().plusMonths(1))
                            .category(Categories.HOUSE)
                            .member(member)
                            .build()
            );

            member.getCategoriesLevels().addAll(levels);
            member.getRoutines().addAll(routines);

            memberRepository.save(member);
        };
    }
}
