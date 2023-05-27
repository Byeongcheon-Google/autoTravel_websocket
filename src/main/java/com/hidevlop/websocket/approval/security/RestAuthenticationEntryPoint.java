package com.hidevlop.websocket.approval.security;



import com.hidevlop.websocket.approval.exception.JwtErrorCode;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = request.getAttribute("exception").toString();



        if(exception == null) {
            setResponse(response, JwtErrorCode.UNKNOWN_ERROR);
        }
        //잘못된 타입의 토큰인 경우
        else if(exception.equals(JwtErrorCode.WRONG_TYPE_TOKEN.getCode())) {
            setResponse(response, JwtErrorCode.WRONG_TYPE_TOKEN);
        }
        //토큰 만료된 경우
        else if(exception.equals(JwtErrorCode.EXPIRED_TOKEN.getCode())) {
            setResponse(response, JwtErrorCode.EXPIRED_TOKEN);
        }
        //지원되지 않는 토큰인 경우
        else if(exception.equals(JwtErrorCode.UNSUPPORTED_TOKEN.getCode())) {
            setResponse(response, JwtErrorCode.UNSUPPORTED_TOKEN);
        }
        else {
            setResponse(response, JwtErrorCode.ACCESS_DENIED);
        }
    }

    //한글 출력을 위해 getWriter() 사용
    private void setResponse(HttpServletResponse response, JwtErrorCode code) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", code.getMessage());
        responseJson.put("code", code.getCode());

        response.getWriter().print(responseJson);
    }
}
