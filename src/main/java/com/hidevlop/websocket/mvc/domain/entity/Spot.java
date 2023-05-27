package com.hidevlop.websocket.mvc.domain.entity;

import com.bcgg.path.model.Point.Classification;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Spot {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private Double lat;
    private Double lon;

    @ManyToOne
    private DaySchedule daySchedule;

    @Enumerated(EnumType.STRING)
    private Classification classification;
    private Long stayTimeMinute;
}
