package com.ruty.rutyserver.common.exception;

import com.ruty.rutyserver.domain.member.exception.MemberDuplicatedException;
import com.ruty.rutyserver.domain.member.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExceptionMapper { // 예외 객체 -> 예외 상태로 바꿔주는 mapper

    private static final Map<Class<? extends Exception>, ExceptionSituation> mapper = new LinkedHashMap<>();

    static {
        setUpMemberException();
//        setUpPostException();
//        setUpReplyException();
//        setUpProjectException();
//        setUpReplyException();
    }

    private static void setUpMemberException() {
        mapper.put(MemberNotFoundException.class,
                ExceptionSituation.of("해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND, 1000));
        mapper.put(MemberDuplicatedException.class,
                ExceptionSituation.of("해당 사용자의 정보가 중복됩니다.", HttpStatus.CONFLICT, 1001));
    }

    public static ExceptionSituation getSituationOf(Exception exception) {
        return mapper.get(exception.getClass());
    }

}
