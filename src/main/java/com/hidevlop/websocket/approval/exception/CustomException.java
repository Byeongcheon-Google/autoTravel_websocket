package com.hidevlop.websocket.approval.exception;



import com.hidevlop.websocket.approval.model.type.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;
    private int httpStatusCode;

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
        this.httpStatusCode = errorCode.getHttpStatusCode();
    }
}
