package com.hidevlop.websocket.mvc.domain.repo;


import com.hidevlop.websocket.mvc.domain.entity.DaySchedule;
import com.hidevlop.websocket.mvc.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
public interface DayScheduleRepository extends JpaRepository<DaySchedule, Long> {

    List<DaySchedule> findBySchedule (Schedule schedule);

    Optional<DaySchedule> findByHopeDate (LocalDate date);
}
