package com.hidevlop.websocket.mvc.domain.dto;


import com.hidevlop.websocket.mvc.domain.entity.DaySchedule;
import com.hidevlop.websocket.mvc.domain.entity.Schedule;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayScheduleDto {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endHopeTime;
    private List<LocalTime> mealTime;
    private List<Double> startSpot;
    private List<Double> endSPot;
    private List<SpotDto> spotDtos;

    public DaySchedule toEntity(Schedule schedule){
       return DaySchedule.builder()
               .hopeDate(this.date)
               .startTime(this.startTime)
               .endHopeTime(this.endHopeTime)
               .mealTime(this.mealTime)
               .startSpot(this.startSpot)
               .endSPot(this.endSPot)
               .schedule(schedule)
               .build();
    }
}
