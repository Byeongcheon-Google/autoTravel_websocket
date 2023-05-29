package com.hidevlop.websocket.path.domain.dto;


import com.bcgg.path.model.Point;
import com.hidevlop.websocket.path.domain.type.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceResponseDto {

    private String name;
    private String address;
    private Double lat;
    private Double lng;
    private Point.Classification classification;
    private PlaceType placeType;
    private Long stayTimeHour;
}
