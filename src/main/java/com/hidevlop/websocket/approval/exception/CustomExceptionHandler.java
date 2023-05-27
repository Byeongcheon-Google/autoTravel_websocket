package com.hidevlop.websocket.approval.exception;



import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class CustomExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public CustomErrorResponse handleSingException(CustomException e){
        return new CustomErrorResponse(
                e.getErrorCode(),
                e.getErrorMessage(),
                e.getHttpStatusCode()
        );
    }



}
