package com.hidevlop.websocket.approval.controller;


import com.hidevlop.websocket.approval.exception.CustomException;
import com.hidevlop.websocket.approval.model.dto.Auth;
import com.hidevlop.websocket.approval.security.TokenProvider;
import com.hidevlop.websocket.approval.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {



    private final MemberService memberService;

    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request){
        try {
            memberService.register(request);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("중복된 ID입니다.");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request){
        try
        {
            var member = memberService.authenticate(request);
            var username = member.getUsername();
            var token = this.tokenProvider.generateToken(username, member.getRoles());
            return ResponseEntity.ok(token);

        } catch (CustomException e){
            return ResponseEntity.badRequest().body("로그인이 허용되지 않습니다.");
        }


    }

    @PostMapping("/validation")
    @ResponseBody
    public ResponseEntity<?> validation(
            Principal principal
    ){
        try {
            var result = principal.getName();
            return ResponseEntity.ok(result);
        } catch (RuntimeException e){
            return ResponseEntity.status(401).body("인증이 유효하지 않습니다.");
        }

    }

    @PostMapping("/duplicate")
    public ResponseEntity<?> checkDuplicate(
            @RequestParam String username
    ){
        var result = memberService.checkId(username);
        if (result){
            return ResponseEntity.badRequest().body("중복된 ID 입니다");
        }
        return ResponseEntity.ok("사용 가능한 ID 입니다.");
    }

}
