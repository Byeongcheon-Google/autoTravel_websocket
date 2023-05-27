package com.hidevlop.websocket.approval.exception;

import lombok.Getter;


@Getter
public enum JwtErrorCode {

    UNKNOWN_ERROR(1000L,"토큰이 존재하지 않습니다." ),
    WRONG_TYPE_TOKEN(401L,"변조된 토큰입니다." ),
    EXPIRED_TOKEN(1002L,"만료된 토큰입니다." ),
    UNSUPPORTED_TOKEN(1003L,"변조된 토큰입니다." ),
    ACCESS_DENIED(1004L,"권한이 없습니다.")
    ;


    private Long code;
    private String message;


    JwtErrorCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }


}
