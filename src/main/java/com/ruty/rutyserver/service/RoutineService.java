package com.ruty.rutyserver.service;

import com.ruty.rutyserver.dto.routine.*;
import com.ruty.rutyserver.entity.CategoryLevel;
import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.Routine;
import com.ruty.rutyserver.entity.e.Week;
import com.ruty.rutyserver.exception.MemberNotFoundException;
import com.ruty.rutyserver.exception.RoutineNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public interface RoutineService {
    List<CategoryLevelInfoDto> getAllCategoryLevels();
    Long saveRoutine(Long recommendId, RoutineReq routineReq, String email);
    List<RoutineDto> getAllRoutines();
    Long updateRoutine(Long routineId, RoutineReq updateRoutine, String email);
    void deleteRoutine(Long routineId);
    CategoryLevelDto toggleRoutineStatus(Long routineId, String email);
    List<TodayRoutineDto> getMyTodayRoutine(String email);
    List<CategoryLevelDto> getMyCategoryLevel(String email);
    List<RoutineDto> getMyAllRoutines(String email, String category);
    Long saveCustomRoutine(String email, RoutineReq routineReq);
    Long updateRoutineProgress(Long routineId);
}
