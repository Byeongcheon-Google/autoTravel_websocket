package com.hidevlop.websocket.approval.security;


import com.hidevlop.websocket.approval.exception.JwtErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        String token = this.resolveTokenFromRequest(request);

        try {
            if(StringUtils.hasText(token)){
                //토큰 유효성 검증
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (ExpiredJwtException e){
            //만료 에러
            request.setAttribute("exception", JwtErrorCode.EXPIRED_TOKEN.getCode());
        } catch (MalformedJwtException e){
            //변조 에러
            request.setAttribute("exception", JwtErrorCode.WRONG_TYPE_TOKEN.getCode());
        } catch (NullPointerException e){
            request.setAttribute("exception", JwtErrorCode.UNKNOWN_ERROR.getCode());
        }

        filterChain.doFilter(request,response);
    }

    private String resolveTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader(TOKEN_HEADER);

        if(!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)){
            return token.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}
