package com.hidevlop.websocket.path.domain.entity;

import com.bcgg.path.model.Point.Classification;
import com.hidevlop.websocket.path.domain.type.PlaceType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private Double lat;
    private Double lng;


    private LocalDate hopeDate;

    @ManyToOne
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    private Classification classification;
    @Enumerated(EnumType.STRING)
    private PlaceType placeType;
    private Long stayTimeHour;
}
