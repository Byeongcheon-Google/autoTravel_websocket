package com.hidevlop.mvc.controller;


import com.hidevlop.mvc.domain.dto.ScheduleDto;
import com.hidevlop.mvc.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<?> saveSchedule(
            @RequestBody ScheduleDto.RequestSchedule request
    ){
        var result = scheduleService.saveSchedule(request);
        return ResponseEntity.ok(result);
    }


}
