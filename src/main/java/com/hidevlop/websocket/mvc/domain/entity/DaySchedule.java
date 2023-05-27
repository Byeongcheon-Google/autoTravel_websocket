package com.hidevlop.websocket.mvc.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DaySchedule {

    @Id
    private LocalDate hopeDate;


    private LocalTime startTime;
    private LocalTime endHopeTime;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalTime> mealTime;

    @ElementCollection
    private List<Double> startSpot;
    @ElementCollection
    private List<Double> endSPot;

    @ManyToOne
    private Schedule schedule;
}
