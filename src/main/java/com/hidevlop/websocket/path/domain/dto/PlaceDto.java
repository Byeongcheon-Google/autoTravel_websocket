package com.hidevlop.websocket.path.domain.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDto {

    private String username;
    private Long id;
    private String name;
    private String address;
    private Double lat;
    private Double lng;
    private String classification;
    private Long stayTimeHour;

}
