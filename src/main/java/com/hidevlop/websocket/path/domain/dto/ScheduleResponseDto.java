package com.hidevlop.websocket.path.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScheduleResponseDto {

    private String scheduleName;

    private List<DayScheduleResponseDto> dayScheduleResponses;


}
