package com.hidevlop.websocket.mvc.service;


import com.hidevlop.websocket.approval.exception.CustomException;
import com.hidevlop.websocket.approval.model.type.ErrorCode;
import com.hidevlop.websocket.model.ChatMessage;
import com.hidevlop.websocket.model.ChatMessage.MessageType;
import com.hidevlop.websocket.model.Command;
import com.hidevlop.websocket.mvc.domain.dto.*;
import com.hidevlop.websocket.mvc.domain.dto.ScheduleDto.RequestSchedule;
import com.hidevlop.websocket.mvc.domain.entity.DaySchedule;
import com.hidevlop.websocket.mvc.domain.entity.Schedule;
import com.hidevlop.websocket.mvc.domain.repo.DayScheduleRepository;
import com.hidevlop.websocket.mvc.domain.repo.ScheduleRepository;
import com.hidevlop.websocket.mvc.domain.repo.SpotRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DayScheduleRepository dayScheduleRepository;
    private final SpotRepository spotRepository;
    private final MessageParser messageParser;

    /**
     * 처음 공용 방에 들어왔을 때, roomId를 제외하고 빈값을 저장
     *
     * @param roomId
     */
    public void saveSchedule(String roomId) {

        RequestSchedule request = this.createEmptyRequest();
        request.setRoomId(roomId);

        Schedule schedule = scheduleRepository.save(request.toEntity());

        List<DayScheduleDto> dayScheduleDtos = request.getDayScheduleDtos();

        for (DayScheduleDto dayscheduledto : dayScheduleDtos) {
            DaySchedule daySchedule =
                    dayScheduleRepository.save(dayscheduledto.toEntity(schedule));

            List<SpotDto> spotDtos = dayscheduledto.getSpotDtos();
            for (SpotDto spotDto : spotDtos) {
                spotRepository.save(spotDto.toEntity(daySchedule));
            }
        }

    }

    public RequestSchedule createEmptyRequest() {

        RequestSchedule requestSchedule = new RequestSchedule();
        requestSchedule.setScheduleName("");
        requestSchedule.setDayScheduleDtos(Collections.singletonList(new DayScheduleDto()));

        DayScheduleDto daySchedule = new DayScheduleDto();
        daySchedule.setDate(null);
        daySchedule.setStartTime(null);
        daySchedule.setEndHopeTime(null);
        daySchedule.setMealTime(Collections.emptyList());
        daySchedule.setStartSpot(Collections.emptyList());
        daySchedule.setEndSPot(Collections.emptyList());
        daySchedule.setSpotDtos(Collections.emptyList());

        requestSchedule.getDayScheduleDtos().add(daySchedule);

        return requestSchedule;
    }


    public void updateSchedule(ChatMessage chatMessage) {



        String type = chatMessage.getCommand();
        String messages = chatMessage.getMessage();


        if (Command.TITLE.toString().equals(type)) {
            Schedule schedule = scheduleRepository.findByRoomId(chatMessage.getRoomId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

            schedule.setScheduleName(messages);
            scheduleRepository.save(schedule);


        } else if (Command.STARTTIME.toString().equals(type)) {

            HopeTimeDto hopeTimeDto = messageParser.parseHopeTime(messages, type);

            DaySchedule daySchedule = dayScheduleRepository.findByHopeDate(hopeTimeDto.getLocalDate())
                    .orElseThrow( () -> new CustomException(ErrorCode.NOT_FOUND_DAYSCHEUDLE));

            daySchedule.setStartTime(hopeTimeDto.getTimes().get(0));

            dayScheduleRepository.save(daySchedule);

        } else if (Command.ENDTIME.toString().equals(type)) {

            HopeTimeDto hopeTimeDto = messageParser.parseHopeTime(messages, type);

            DaySchedule daySchedule = dayScheduleRepository.findByHopeDate(hopeTimeDto.getLocalDate())
                    .orElseThrow( () -> new CustomException(ErrorCode.NOT_FOUND_DAYSCHEUDLE));

            daySchedule.setEndHopeTime(hopeTimeDto.getTimes().get(0));

            dayScheduleRepository.save(daySchedule);

        } else if (Command.MEALTIMES.toString().equals(type)) {

            HopeTimeDto hopeTimeDto = messageParser.parseHopeTime(messages, type);

            DaySchedule daySchedule = dayScheduleRepository.findByHopeDate(hopeTimeDto.getLocalDate())
                    .orElseThrow( () -> new CustomException(ErrorCode.NOT_FOUND_DAYSCHEUDLE));

            daySchedule.setMealTime(hopeTimeDto.getTimes());

            dayScheduleRepository.save(daySchedule);

        } else if (Command.POINTS.toString().equals(type)) {

            PlacesDto placesDto = messageParser.parsePlaces(messages, type);



        } else if (Command.STARTTIME.toString().equals(type)) {


        } else {

        }


    }


}
