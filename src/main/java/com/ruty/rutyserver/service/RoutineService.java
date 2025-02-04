package com.ruty.rutyserver.service;

import com.ruty.rutyserver.dto.member.MemberInfoDto;
import com.ruty.rutyserver.dto.routine.CategoryLevelDto;
import com.ruty.rutyserver.dto.routine.TodayRoutineDto;
import com.ruty.rutyserver.entity.Level;
import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.e.Week;
import com.ruty.rutyserver.exception.MemberNotFoundException;
import com.ruty.rutyserver.exception.RoutineNotFoundException;
import com.ruty.rutyserver.repository.LevelRepository;
import com.ruty.rutyserver.repository.MemberRepository;
import com.ruty.rutyserver.repository.RecommendRepository;
import com.ruty.rutyserver.dto.routine.RoutineDto;
import com.ruty.rutyserver.repository.RoutineRepository;
import com.ruty.rutyserver.dto.routine.RoutineReq;
import com.ruty.rutyserver.entity.Routine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final MemberRepository memberRepository;
    private final RecommendRepository recommendRepository;
    private final LevelRepository levelRepository;

    @Transactional
    public Long saveRoutine(Long recommendId, RoutineReq routineReq, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        recommendRepository.deleteById(recommendId);
        Routine save = routineRepository.save(Routine.toEntity(routineReq, member));
        return save.getId();
    }

    @Transactional
    public List<RoutineDto> getAllRoutines() {
        return routineRepository.findAll()
                .stream()
                .map(RoutineDto::of) // 엔티티를 DTO로 변환
                .toList(); // 최종 리스트로 변환
    }


    @Transactional
    public CategoryLevelDto toggleRoutineStatus(Long routineId, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Routine routine = routineRepository.findById(routineId).orElseThrow(RoutineNotFoundException::new);
        routine.setIsDone(!routine.getIsDone());

        List<Level> categoryLevels = member.getCategoriesLevel();
        for(Level categoryLevel : categoryLevels) {
            if(categoryLevel.getCategory().getValue().equals(routine.getCategory().getValue())) {
                Long level = categoryLevel.getLevel();
                Long totalPoints = categoryLevel.getTotalPoints();

                // 비활성화일 경우
                if(!routine.getIsDone()) {
                    categoryLevel.pointDown();
                    Long prevLevelThreshold = getRequiredPoints(level); // 현재 레벨의 최소 요구 포인트

                    // 현재 포인트가 이전 레벨의 최소 포인트보다 작으면 레벨 다운
                    if (categoryLevel.getTotalPoints() < prevLevelThreshold) {
                        categoryLevel.levelDown();
                    }
                }
                // 활성화일 경우
                else {
                    categoryLevel.pointUp(); // 누적 포인트 증가
                    Long nextLevelThreshold = getRequiredPoints(level + 1);
                    log.info("level range: " + nextLevelThreshold);

                    // 현재 누적 포인트가 다음 레벨의 요구 포인트를 넘었으면 레벨 증가
                    if (totalPoints >= nextLevelThreshold) {
                        categoryLevel.levelUp();
                    }
                }

                return CategoryLevelDto.of(categoryLevel);
            }
        }
        return null;
    }

    public List<TodayRoutineDto> getMyTodayRoutine(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        // 2. 오늘 요일 가져오기
        Week today = Week.valueOf(LocalDate.now().getDayOfWeek().name().substring(0, 3).toUpperCase());

        // 3. 오늘 요일에 해당하는 루틴 조회
        List<Routine> todayRoutines = routineRepository.findTodayRoutines(member.getId(), today);

        // 4. DTO 변환 후 반환(종료일 초과 안된 것만 필터링) -> 배치 서버 제공시 삭제
        return todayRoutines.stream()
                .filter(routine -> !routine.getEndDate().isBefore(LocalDate.now())) // 종료일 초과된 것 제외
                .map(TodayRoutineDto::of)
                .collect(Collectors.toList());
    }

    public List<CategoryLevelDto> getMyCategoryLevel(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<Level> categoryLevels = levelRepository.findAllByMemberId(member.getId());
        return categoryLevels.stream()
                .map(CategoryLevelDto::of)
                .collect(Collectors.toList());
    }

    private Long getRequiredPoints(Long level) {
        if (level <= 1) return 0L; // 레벨 1은 최소 포인트 0
        return (long) 5 * ((level - 1) * level) / 2; // 누적 요구 포인트 계산
    }


}
