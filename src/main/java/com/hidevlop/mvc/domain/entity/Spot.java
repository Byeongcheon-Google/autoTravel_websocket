package com.hidevlop.mvc.domain.entity;

import com.bcgg.path.model.Point.Classification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spot {

    @Id
    @GeneratedValue
    private Long id;
    private Double lat;
    private Double lon;

    @ManyToOne
    private DaySchedule daySchedule;

    @Enumerated(EnumType.STRING)
    private Classification classification;
    private Long stayTimeMinute;
}
