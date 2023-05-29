package com.hidevlop.websocket.path.service;


import com.bcgg.path.model.Point;
import com.hidevlop.websocket.approval.exception.CustomException;
import com.hidevlop.websocket.approval.model.type.ErrorCode;
import com.hidevlop.websocket.chatiing.model.ChatMessage;
import com.hidevlop.websocket.chatiing.model.Command;
import com.hidevlop.websocket.path.domain.dto.*;
import com.hidevlop.websocket.path.domain.entity.DaySchedule;
import com.hidevlop.websocket.path.domain.entity.Place;
import com.hidevlop.websocket.path.domain.entity.Schedule;
import com.hidevlop.websocket.path.domain.repo.DayScheduleRepository;
import com.hidevlop.websocket.path.domain.repo.ScheduleRepository;
import com.hidevlop.websocket.path.domain.repo.PlaceRepository;
import com.hidevlop.websocket.path.domain.type.PlaceType;
import lombok.RequiredArgsConstructor;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DayScheduleRepository dayScheduleRepository;
    private final PlaceRepository placeRepository;
    private final MessageParser messageParser;


    public Long saveSchedule(String username) {
        Schedule schedule = new Schedule();

        schedule.setUsername(new ArrayList<>());
        schedule.getUsername().add(username);
        Long scheduleId = scheduleRepository.save(schedule).getId();

        return scheduleId;
    }

    public Long inviteRoom(String roomId, String username){
        Schedule schedule = scheduleRepository.findByRoomId(roomId)
                .orElseThrow( () -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

        schedule.getUsername().add(username);
        Long scheduleId = scheduleRepository.save(schedule).getId();
        return scheduleId;
    }

    public String updateRoomId(String roomId, Long scheduleId) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow( () -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

        schedule.setRoomId(roomId);
        scheduleRepository.save(schedule);

        return roomId;
    }

    public List<Schedule> readAllSchedule(String username){
        List<Schedule> schedules = scheduleRepository.findAllByUsername(username);

        if (schedules.size() == 0){
            throw new CustomException(ErrorCode.NOT_FOUND_SCHEDULE);
        }

        return schedules;
    }

    public void updateSchedule(ChatMessage chatMessage) {


        String type = chatMessage.getCommand();
        String messages = chatMessage.getMessage();
        String roomId = chatMessage.getRoomId();


        if (Command.TITLE.toString().equals(type)) {
            Schedule schedule = scheduleRepository.findByRoomId(chatMessage.getRoomId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

            schedule.setScheduleName(messages);
            scheduleRepository.save(schedule);


        } else if (Command.STARTTIME.toString().equals(type)) {

            HopeTimeDto hopeTimeDto = messageParser.parseHopeTime(messages, type);
            DaySchedule daySchedule = new DaySchedule();
            try {
                daySchedule = dayScheduleRepository.findByHopeDate(hopeTimeDto.getLocalDate())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));


            } catch (CustomException e) {
                Schedule schedule = scheduleRepository.findByRoomId(roomId)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
                daySchedule.setSchedule(schedule);
            }

            daySchedule.setHopeDate(hopeTimeDto.getLocalDate());
            daySchedule.setStartTime(hopeTimeDto.getTimes().get(0));

            dayScheduleRepository.save(daySchedule);

        } else if (Command.ENDTIME.toString().equals(type)) {

            HopeTimeDto hopeTimeDto = messageParser.parseHopeTime(messages, type);
            DaySchedule daySchedule = new DaySchedule();
            try {
                daySchedule = dayScheduleRepository.findByHopeDate(hopeTimeDto.getLocalDate())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DAYSCHEUDLE));

            } catch (CustomException e) {
                Schedule schedule = scheduleRepository.findByRoomId(roomId)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

                daySchedule.setSchedule(schedule);
            }

            daySchedule.setHopeDate(hopeTimeDto.getLocalDate());
            daySchedule.setEndHopeTime(hopeTimeDto.getTimes().get(0));

            dayScheduleRepository.save(daySchedule);

        } else if (Command.MEALTIMES.toString().equals(type)) {

            HopeTimeDto hopeTimeDto = messageParser.parseHopeTime(messages, type);
            DaySchedule daySchedule = new DaySchedule();
            try {
                daySchedule = dayScheduleRepository.findByHopeDate(hopeTimeDto.getLocalDate())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DAYSCHEUDLE));
            } catch (CustomException e) {

                Schedule schedule = scheduleRepository.findByRoomId(roomId)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

                daySchedule.setSchedule(schedule);
            }


            daySchedule.setHopeDate(hopeTimeDto.getLocalDate());
            daySchedule.setMealTime(hopeTimeDto.getTimes());

            dayScheduleRepository.save(daySchedule);

        } else if (Command.POINTS.toString().equals(type)) {

            PlacesDto placesDto = messageParser.parsePlaces(messages, type);
            for (PlaceDto placeDto : placesDto.getPlaces()) {

                Schedule schedule = new Schedule();
                Place place = new Place();
                try {
                    place = placeRepository.findById(placeDto.getId())
                            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PLACE));
                } catch (CustomException e) {

                    schedule = scheduleRepository.findByRoomId(roomId)
                            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));


                }
                place.setSchedule(schedule);
                place.setHopeDate(placesDto.getDate());
                place.setName(placeDto.getName());
                place.setAddress(placeDto.getAddress());
                place.setPlaceType(PlaceType.MIDDLE);
                place.setLat(placeDto.getLat());
                place.setLng(placeDto.getLng());
                place.setStayTimeHour(placeDto.getStayTimeHour());
                try {
                    place.setClassification(Point.Classification.valueOf(placeDto.getClassification()));
                } catch (IllegalArgumentException e) {
                    throw new CustomException(ErrorCode.INVALID_ENUM_VALUE);
                }

                placeRepository.save(place);

            }

        } else if (Command.STARTPLACE.toString().equals(type)) {

            Place place = this.getPlace(type, messages, roomId);
            place.setPlaceType(PlaceType.STARTPLACE);

            placeRepository.save(place);


        } else if (Command.ENDPLACE.toString().equals(type)) {

            Place place = this.getPlace(type, messages, roomId);
            place.setPlaceType(PlaceType.STARTPLACE);

            placeRepository.save(place);
        }


    }

    @NotNull
    private Place getPlace(String type, String messages, String roomId) {
        PlacesDto placesDto = messageParser.parsePlaces(messages, type);
        PlaceDto placeDto = placesDto.getPlaces().get(0);

        Schedule schedule = new Schedule();
        Place place = new Place();
        try {
            place = placeRepository.findById(placeDto.getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PLACE));
        } catch (CustomException e) {

            schedule = scheduleRepository.findByRoomId(roomId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));


        }
        place.setSchedule(schedule);
        place.setHopeDate(placesDto.getDate());
        place.setName(placeDto.getName());
        place.setAddress(placeDto.getAddress());
        place.setClassification(Point.Classification.valueOf(placeDto.getClassification()));
        place.setLat(placeDto.getLat());
        place.setLng(placeDto.getLng());
        place.setStayTimeHour(placeDto.getStayTimeHour());
        return place;
    }

    public ScheduleResponseDto readPerOneSchedule(Long scheduleId) {

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto();
        List<DayScheduleResponseDto> dayScheduleResponseDtos = new ArrayList<>();
        List<PlaceResponseDto> placeResponseDtos = new ArrayList<>();

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

        List<DaySchedule> daySchedules = dayScheduleRepository.findBySchedule(schedule);


        scheduleResponseDto.setScheduleName(schedule.getScheduleName());


        for (DaySchedule daySchedule : daySchedules) {

            List<Place> places = placeRepository.findAllByHopeDateAndSchedule(daySchedule.getHopeDate(), schedule);

            for (Place place : places) {
                placeResponseDtos.add(PlaceResponseDto.builder()
                        .name(place.getName())
                        .address(place.getAddress())
                        .lat(place.getLat())
                        .lng(place.getLng())
                        .classification(place.getClassification())
                        .placeType(place.getPlaceType())
                        .stayTimeHour(place.getStayTimeHour())
                        .build()
                );
            }


            dayScheduleResponseDtos.add(
                    DayScheduleResponseDto.builder()
                            .hopeDate(daySchedule.getHopeDate())
                            .startTime(daySchedule.getStartTime())
                            .endHopeTime(daySchedule.getEndHopeTime())
                            .mealTime(daySchedule.getMealTime())
                            .placeResponseDtos(placeResponseDtos)
                            .build()
            );

            scheduleResponseDto.setDayScheduleResponses(dayScheduleResponseDtos);


        }
        return scheduleResponseDto;
    }


    public void removeSchedule(String username, Long scheduleId){

        Schedule schedule = scheduleRepository.findByUsernameAndId(username, scheduleId)
                .orElseThrow( () -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

        scheduleRepository.deleteById(scheduleId);
    }

}
