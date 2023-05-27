package com.hidevlop.websocket.controller;

import com.hidevlop.websocket.mvc.service.ScheduleService;
import com.hidevlop.websocket.approval.security.TokenProvider;
import com.hidevlop.websocket.model.ChatMessage;
import com.hidevlop.websocket.model.Command;
import com.hidevlop.websocket.service.ChatService;
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
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @RequestHeader("token") String token) {
        String nickname = tokenProvider.getUserName(token);


        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);


        //TODO


        //첫번째 저장
        String checkRoomId = "a";

        if (!checkRoomId.equals(message.getRoomId()) && ChatMessage.MessageType.ENTER.equals(message.getType())) {

            checkRoomId = message.getRoomId();
            scheduleService.saveSchedule(checkRoomId);

        }
        //수정
        if (!Command.VIEWINGLOCATION.toString().equals(message.getCommand()) && ChatMessage.MessageType.TALK.equals(message.getType())) {

//            scheduleService.parseMap(message.getMessage(), message.getCommand());
        }

        // Websocket에 발행된 메시지를 redis로 발행(publish)
        chatService.sendChatMessage(message);


    }


}
