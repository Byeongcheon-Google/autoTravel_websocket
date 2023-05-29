package com.hidevlop.websocket.chatiing.controller;

import com.hidevlop.websocket.chatiing.model.ChatMessage;
import com.hidevlop.websocket.chatiing.model.Command;
import com.hidevlop.websocket.chatiing.service.ChatService;
import com.hidevlop.websocket.path.service.ScheduleService;
import com.hidevlop.websocket.approval.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final TokenProvider tokenProvider;
    private final ChatService chatService;
    private final ScheduleService scheduleService;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     *
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @RequestHeader("X-AUTH-TOKEN") String token ) {
        String nickname = tokenProvider.getUserName(token);


        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);


        if (!Command.VIEWINGLOCATION.toString().equals(message.getCommand()) && ChatMessage.MessageType.TALK.equals(message.getType())) {
            scheduleService.updateSchedule(message);
        }

        // Websocket에 발행된 메시지를 redis로 발행(publish)
        chatService.sendChatMessage(message);


    }


}
