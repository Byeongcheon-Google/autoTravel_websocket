package com.hidevlop.websocket.approval.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),"가입되지 않은 사용자입니다."),
    DUPLICATE_USER_ID(HttpStatus.BAD_REQUEST.value(),"중복된 ID 입니다."),
    NOT_SAME_PW(HttpStatus.BAD_REQUEST.value(),"비밀번호가 일치하지 않습니다."),
    NOT_FOUND_SCHEDULE(HttpStatus.BAD_REQUEST.value(),"등록된 스케줄이 없습니다."),
    NOT_FOUND_DAYSCHEUDLE(HttpStatus.BAD_REQUEST.value(), "등록된 하루 일정이 없습니다."),
    NOT_FOUND_PLACE(HttpStatus.BAD_REQUEST.value(),"등록된 장소가 없습니다"),
    INVALID_ENUM_VALUE(HttpStatus.BAD_REQUEST.value(), "유효한 enum값이 아닙니다.")

    ;

    private final int httpStatusCode;
    private final String description;

}
