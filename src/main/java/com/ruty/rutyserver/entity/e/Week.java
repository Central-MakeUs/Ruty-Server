package com.ruty.rutyserver.entity.e;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;

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

    public static Week fromDayOfWeek(DayOfWeek dayOfWeek) {
        for (Week week : values()) {
            if (week.key.equals(dayOfWeek.name())) {
                return week;
            }
        }
        throw new IllegalArgumentException("Invalid day of week: " + dayOfWeek);
    }
}
