package com.hidevlop.websocket.mvc.domain.dto;


import com.bcgg.path.model.Point;
import com.hidevlop.websocket.mvc.domain.entity.DaySchedule;
import com.hidevlop.websocket.mvc.domain.entity.Spot;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpotDto {

    private Double lat;
    private Double lon;
    private Point.Classification classification;
    private Long stayTimeMinute;

    public Spot toEntity(DaySchedule daySchedule){
        return Spot.builder()
                .lat(this.lat)
                .lon(this.lon)
                .classification(this.classification)
                .stayTimeMinute(this.stayTimeMinute)
                .daySchedule(daySchedule)
                .build();
    }
}
