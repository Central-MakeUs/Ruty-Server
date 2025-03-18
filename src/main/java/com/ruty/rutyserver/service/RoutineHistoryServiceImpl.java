package com.ruty.rutyserver.service;

import com.ruty.rutyserver.dto.routine_history.RoutineHistoryCountRep;
import com.ruty.rutyserver.dto.routine_history.RoutineHistoryRep;
import com.ruty.rutyserver.entity.Routine;
import com.ruty.rutyserver.entity.RoutineHistory;
import com.ruty.rutyserver.entity.e.Week;
import com.ruty.rutyserver.exception.RoutineNotFoundException;
import com.ruty.rutyserver.repository.RoutineHistoryRepository;
import com.ruty.rutyserver.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineHistoryServiceImpl implements RoutineHistoryService{

    private final RoutineHistoryRepository routineHistoryRepository;
    private final RoutineRepository routineRepository;

    @Override
    public List<RoutineHistoryRep> getRoutineHistoryInMonth(Long routineId, Long year, Long month) {
        /**
         * 1. 요청온 연도와 달이 유효한지 판단함.
         * 2. 연도와 달을 통해서 LocalDate 형식에 맞춰서 만듬.
         * 3. 해당 연도와 달 시에이 존재하며, routineId가 같은 RoutineHistory 를 조회함.
         * 4. 이를 dto로 변환해서 요청 보냄.
         * */

        List<RoutineHistory> histories = routineHistoryRepository.findAllByRoutineIdAndDate(routineId, year, month);

        // 3. DTO 변환 후 반환
        return histories.stream()
                .map(history -> RoutineHistoryRep.builder()
                        .date(history.getDate())
                        .isDone(history.getIsDone())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public RoutineHistoryCountRep getRoutineHistoryCount(Long routineId) {

        List<RoutineHistory> routineHistories = routineHistoryRepository.findAllByRoutineId(routineId);
        routineHistories.sort(Comparator.comparing(RoutineHistory::getDate).reversed()); // 최신순 정렬

        Routine routine = routineRepository.findById(routineId).orElseThrow(RoutineNotFoundException::new);
        List<Week> activeDays = routine.getWeeks(); // 사용자가 설정한 수행 요일
        LocalDate startDate = routine.getStartDate();
        LocalDate endDate = routine.getEndDate();

        long totalCount = 0;
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) { // 종료일 이후가 아닐 때까지 반복
            if (activeDays.contains(Week.fromDayOfWeek(currentDate.getDayOfWeek()))) {
                totalCount++;
            }
            currentDate = currentDate.plusDays(1);
        }

        long completedCount = routineHistories.stream()
                .filter(RoutineHistory::getIsDone)
                .count();
        long streakCount = 0;

        if(routine.getIsDone()) {
            totalCount++;
            completedCount++;
            streakCount++;
        }

        if(!routineHistories.isEmpty()) {
            LocalDate lastDate = routineHistories.get(0).getDate(); // 최신 날짜로 설정

            for (RoutineHistory history : routineHistories) {
                if (!history.getIsDone()) break; // 중간에 실패가 나오면 streak 중단
                if (!activeDays.contains(Week.fromDayOfWeek(history.getDate().getDayOfWeek()))) break;
                streakCount++;

                // 사용자가 설정한 요일 중에서 가장 최근 날짜인지 확인
//            if (history.getDate().isBefore(lastDate) || history.getDate().isEqual(lastDate)) {
//                streakCount++;
//                lastDate = history.getDate();
//            } else {
//                break;
//            }
            }
        }

        return RoutineHistoryCountRep.builder()
                .totalCount(totalCount)
                .completedCount(completedCount)
                .streakCount(streakCount)
                .build();
    }
}
