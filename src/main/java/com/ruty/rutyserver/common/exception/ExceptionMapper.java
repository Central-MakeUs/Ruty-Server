package com.ruty.rutyserver.common.exception;

import com.ruty.rutyserver.exception.*;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExceptionMapper { // 예외 객체 -> 예외 상태로 바꿔주는 mapper

    private static final Map<Class<? extends Exception>, ExceptionSituation> mapper = new LinkedHashMap<>();

    static {
        setUpMemberException();
        setUpRoutineException();
        setUpRecommendException();
        setUpJwtException();
        setUpEtcException();
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
        mapper.put(RoutineProgressException.class,
                ExceptionSituation.of("해당 루틴은 진행 상태를 변경할 수 없습니다.", HttpStatus.CONFLICT, 2001));
    }
    private static void setUpRecommendException() {
        mapper.put(RecommendNotFoundException.class,
                ExceptionSituation.of("해당 추천루틴은 존재하지 않습니다.", HttpStatus.NOT_FOUND, 3000));
    }
    private static void setUpJwtException() {
        mapper.put(JwtException.class,
                ExceptionSituation.of("로그인 토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST, 7000));
        mapper.put(JwtAuthenticationException.class,
                ExceptionSituation.of("권한이 없습니다.", HttpStatus.UNAUTHORIZED, 7001));
        mapper.put(JwtAccessDeniedException.class,
                ExceptionSituation.of("인증되지 않은 사용자입니다. 로그인후 이용해주세요.", HttpStatus.FORBIDDEN, 7002));

    }
    private static void setUpEtcException() {
        mapper.put(SocialTypeException.class,
                ExceptionSituation.of("유효하지 않은 socialType이 접근되었습니다.", HttpStatus.BAD_REQUEST, 9000));
    }
    public static ExceptionSituation getSituationOf(Exception exception) {
        return mapper.get(exception.getClass());
    }

}
