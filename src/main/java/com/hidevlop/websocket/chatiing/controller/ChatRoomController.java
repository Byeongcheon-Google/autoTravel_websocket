package com.hidevlop.websocket.chatiing.controller;


import com.hidevlop.websocket.approval.security.TokenProvider;
import com.hidevlop.websocket.chatiing.model.ChatRoom;
import com.hidevlop.websocket.chatiing.repo.ChatRoomRepository;
import com.hidevlop.websocket.path.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ScheduleService scheduleService;
    private final TokenProvider tokenProvider;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }


    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomRepository.getUserCount(room.getRoomId())));
        return chatRooms;
    }


    // 채팅방 생성
    @PostMapping("/createRoom")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name,
                               @RequestParam Long scheduleId) {
        ChatRoom chatRoom = chatRoomRepository.createChatRoom(name);
        scheduleService.updateRoomId(chatRoom.getRoomId(), scheduleId);
        return chatRoom;
    }



    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(
            Model model,
            @PathVariable String roomId,
            @RequestHeader Long scheduleId
    ) {
        model.addAttribute("roomId", roomId);

        //TODO
        scheduleService.updateRoomId(roomId, scheduleId);

        return "/chat/roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }


}