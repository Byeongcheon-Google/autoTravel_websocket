package com.hidevlop.websocket.path.controller;

import com.hidevlop.websocket.approval.service.MemberService;
import com.hidevlop.websocket.path.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")

public class ScheduleController {

   private final ScheduleService scheduleService;
   private final MemberService memberService;


    @PostMapping("/generation")
    public ResponseEntity<?> saveSchedule(
            @RequestHeader(name = "X-AUTH-TOKEN") String token
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = memberService.loadUserByUsername(authentication.getName());

        var result = scheduleService.saveSchedule(userDetails.getUsername());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/invite")
    public ResponseEntity<?> inviteScheule(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestParam String roomId
    ){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = memberService.loadUserByUsername(authentication.getName());
            Long scheduleId = scheduleService.inviteRoom(roomId, userDetails.getUsername());
            var result = scheduleService.readPerOneSchedule(scheduleId);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("일정에 사용자 추가를 실패했습니다.");
        }
    }

    @GetMapping("/allschedule")
    public ResponseEntity<?> readAllSchedule(
            @RequestHeader(name = "X-AUTH-TOKEN") String token
    ){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = memberService.loadUserByUsername(authentication.getName());

            var result = scheduleService.readAllSchedule(userDetails.getUsername());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("일정 조회에 실패했습니다. :(");
        }
    }

    @GetMapping("/oneschedule")
    public ResponseEntity<?> readScheduleOne(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestParam Long scheduleId
    ){
        try {
            var result = scheduleService.readPerOneSchedule(scheduleId);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("일정이 없거나 일정을 불러오는데 실패했습니다. :(");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeSchedule(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestParam Long scheduleId
    ){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = memberService.loadUserByUsername(authentication.getName());

            scheduleService.removeSchedule(userDetails.getUsername(), scheduleId);
            return ResponseEntity.ok("삭제에 성공하셨습니다! :)");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("삭제에 실패하셨어요 :(");
        }
    }

}
