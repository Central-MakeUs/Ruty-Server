package com.ruty.rutyserver.entity.e;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoutineProgress {
    ONGOING("ONGOING"),   // 진행중
    DONE("DONE"),      // 완료
    GIVE_UP("GIVE_UP");   // 중도포기

    private final String value;
}
