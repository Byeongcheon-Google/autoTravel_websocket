package com.hidevlop.websocket.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ChatRoom {

    private String roomId;
    private String name;

    public static ChatRoom create(String name){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }

    //    private Set<WebSocketSession> sessions = new HashSet<>();

//    @Builder
//    public ChatRoom(String roomId, String name){
//        this.roomId = roomId;
//        this.name = name;
//    }

//    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService){
//        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)){
//            sessions.add(session);
//            chatMessage.setMessage(chatMessage.getSender()+"님이 입장하셨습니다.");
//        }
//        sendMessage(chatMessage, chatService);
//    }
//
//    public <T> void sendMessage(T message, ChatService chatService){
//        sessions.parallelStream().forEach(sessions -> chatService.sendMessage(sessions, message));
//    }
}
