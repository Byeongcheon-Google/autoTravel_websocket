package com.hidevlop.websocket.path.domain.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PlacesDto {

    private LocalDate date;
    private List<PlaceDto> places;

}
