package com.hidevlop.websocket.approval.model.dto;


import com.hidevlop.websocket.approval.model.entity.Member;
import lombok.Data;

import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.List;

public class Auth {

    @Data
    public static class SignIn{
        private String username;
        private String password;
    }

    @Data
    public static class SignUp{
        private String username;
        private String password;


        public Member toEntity(List<String> roles){
            return Member.builder()
                    .username(this.username)
                    .password(this.password)
                    .roles(roles)
                    .build();
        }
    }
}
