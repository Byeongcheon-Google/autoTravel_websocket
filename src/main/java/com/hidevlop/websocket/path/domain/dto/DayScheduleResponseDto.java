package com.hidevlop.websocket.path.domain.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DayScheduleResponseDto {


    private LocalDate hopeDate;
    private LocalTime startTime;
    private LocalTime endHopeTime;
    private List<LocalTime> mealTime;

    private List<PlaceResponseDto> placeResponseDtos;
}


