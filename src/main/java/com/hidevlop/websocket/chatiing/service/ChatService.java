package com.hidevlop.websocket.chatiing.service;

import com.hidevlop.websocket.chatiing.model.ChatMessage;
import com.hidevlop.websocket.chatiing.model.Command;
import com.hidevlop.websocket.chatiing.repo.ChatRoomRepository;
import com.hidevlop.websocket.path.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final ScheduleService scheduleService;
    private final ChatRoomRepository chatRoomRepository;

    /**
     * destination정보에서 roomId 추출
     */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    /**
     * 방 유저수 발송
     */
    public void sendUserCount(ChatMessage chatMessage, long userCount) {


        chatMessage.setMessage(String.valueOf(userCount));
        chatMessage.setCommand(String.valueOf(Command.INFO));
        chatMessage.setSender("SYSTEM");
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }
    /**
     * 채팅방에 메시지 발송
     */
    public void sendChatMessage(ChatMessage chatMessage) {

        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {

        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에서 나갔습니다.");
            chatMessage.setSender("[알림]");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }
}
