package com.ruty.rutyserver.entity.e;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Categories {
    HOUSE("HOUSE"),
    LEISURE("LEISURE"),
    SELFCARE("SELFCARE"),
    MONEY("MONEY");

    private final String value;

}
