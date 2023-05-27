package com.hidevlop.websocket.mvc.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class HopeTimeDto {

    private LocalDate localDate;
    private List<LocalTime> times;
}
