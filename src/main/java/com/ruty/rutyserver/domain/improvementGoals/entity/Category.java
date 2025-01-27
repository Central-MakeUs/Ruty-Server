package com.ruty.rutyserver.domain.improvementGoals.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    HOUSE("HOUSE"),
    LEISURE("LEISURE"),
    SELFCARE("SELFCARE"),
    MONEY("MONEY");

    private final String value;

}
