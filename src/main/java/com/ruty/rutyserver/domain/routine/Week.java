package com.ruty.rutyserver.domain.routine;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Week {
    MON("MONDAY"),
    TUE("TUESDAY"),
    WED("WEDNESDAY"),
    THU("THURSDAY"),
    FRI("FRIDAY"),
    SAT("SATURDAY"),
    SUN("SUNDAY");

    private final String key;
}
