package com.hidevlop.websocket.path.domain.entity;

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
    @GeneratedValue
    private Long id;


    private LocalDate hopeDate;


    private LocalTime startTime;
    private LocalTime endHopeTime;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalTime> mealTime;

    @ManyToOne
    private Schedule schedule;
}
