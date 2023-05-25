package com.hidevlop.mvc.domain.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endHopeTime;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalTime> mealTime;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Double> startSpot;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Double> endSPot;

    @ManyToOne
    private Schedule schedule;
}
