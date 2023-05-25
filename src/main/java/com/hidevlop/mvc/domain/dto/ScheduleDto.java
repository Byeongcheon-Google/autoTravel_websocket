package com.hidevlop.mvc.domain.dto;


import com.hidevlop.mvc.domain.entity.Schedule;
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
        private List<DayScheduleDto> dayScheduleDtos;


        public Schedule toEntity(){
            return Schedule.builder()
                    .scheduleName(scheduleName)
                    .build();
        }
    }





}
