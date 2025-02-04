package com.ruty.rutyserver.common.exception;

import com.ruty.rutyserver.exception.MemberDuplicatedException;
import com.ruty.rutyserver.exception.MemberNotFoundException;
import com.ruty.rutyserver.exception.RecommendNotFoundException;
import com.ruty.rutyserver.exception.RoutineNotFoundException;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExceptionMapper { // 예외 객체 -> 예외 상태로 바꿔주는 mapper

    private static final Map<Class<? extends Exception>, ExceptionSituation> mapper = new LinkedHashMap<>();

    static {
        setUpMemberException();
        setUpRoutineException();
        setUpRecommendException();
    }

    private static void setUpMemberException() {
        mapper.put(MemberNotFoundException.class,
                ExceptionSituation.of("해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND, 1000));
        mapper.put(MemberDuplicatedException.class,
                ExceptionSituation.of("해당 사용자의 정보가 중복됩니다.", HttpStatus.CONFLICT, 1001));
    }
    private static void setUpRoutineException() {
        mapper.put(RoutineNotFoundException.class,
                ExceptionSituation.of("해당 루틴은 존재하지 않습니다.", HttpStatus.NOT_FOUND, 2000));
    }
    private static void setUpRecommendException() {
        mapper.put(RecommendNotFoundException.class,
                ExceptionSituation.of("해당 추천루틴은 존재하지 않습니다.", HttpStatus.NOT_FOUND, 3000));
    }
    public static ExceptionSituation getSituationOf(Exception exception) {
        return mapper.get(exception.getClass());
    }

}
