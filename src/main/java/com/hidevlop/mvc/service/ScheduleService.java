package com.hidevlop.mvc.service;


import com.hidevlop.mvc.domain.dto.DayScheduleDto;
import com.hidevlop.mvc.domain.dto.ScheduleDto;
import com.hidevlop.mvc.domain.dto.SpotDto;
import com.hidevlop.mvc.domain.entity.DaySchedule;
import com.hidevlop.mvc.domain.entity.Schedule;
import com.hidevlop.mvc.domain.repo.DayScheduleRepository;
import com.hidevlop.mvc.domain.repo.ScheduleRepository;
import com.hidevlop.mvc.domain.repo.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DayScheduleRepository dayScheduleRepository;
    private final SpotRepository spotRepository;

    public String saveSchedule(ScheduleDto.RequestSchedule request){

        Schedule schedule = scheduleRepository.save(request.toEntity());

        List<DayScheduleDto> dayScheduleDtos = request.getDayScheduleDtos();

        for (DayScheduleDto dayScheduleDto : dayScheduleDtos ){
            DaySchedule daySchedule =
                    dayScheduleRepository.save(dayScheduleDto.toEntity(schedule));

            List<SpotDto> spotDtos = dayScheduleDto.getSpotDtos();
            for (SpotDto spotDto : spotDtos){
                spotRepository.save(spotDto.toEntity(daySchedule));
            }
        }

        return "save success";
    }
}
