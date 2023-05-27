package com.hidevlop.websocket.mvc.domain.dto;


import com.hidevlop.websocket.mvc.domain.entity.Schedule;
import lombok.*;

import java.util.List;

public class ScheduleDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RequestSchedule{
        private String scheduleName;
        private String roomId;
        private List<DayScheduleDto> dayScheduleDtos;


        public Schedule toEntity(){
            return Schedule.builder()
                    .scheduleName(scheduleName)
                    .build();
        }
    }





}
