package com.hidevlop.websocket.approval.service;


import com.hidevlop.websocket.approval.exception.CustomException;
import com.hidevlop.websocket.approval.model.dto.Auth;
import com.hidevlop.websocket.approval.model.entity.Member;
import com.hidevlop.websocket.approval.model.repository.MemberRepository;
import com.hidevlop.websocket.approval.model.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "님을 찾을 수 없습니다."));
    }

    public void register(Auth.SignUp member) {
        boolean exist = this.checkId(member.getUsername());
        if (exist) {
            throw new IllegalArgumentException();
        }
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member.toEntity(roles));


    }

    public Member authenticate(Auth.SignIn member) {
        var user = memberRepository.findByUsername(member.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.NOT_SAME_PW);
        }


        return user;
    }

    public Boolean checkId(String username){
        return this.memberRepository.existsByUsername(username);
    }
}
