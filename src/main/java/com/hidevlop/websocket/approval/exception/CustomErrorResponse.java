package com.hidevlop.websocket.approval.exception;



import com.hidevlop.websocket.approval.model.type.ErrorCode;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
    private ErrorCode errorCode;
    private String errorMessage;
    private int httpStatusCode;
}
