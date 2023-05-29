package com.hidevlop.websocket.path.domain.repo;


import com.hidevlop.websocket.path.domain.entity.DaySchedule;
import com.hidevlop.websocket.path.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
public interface DayScheduleRepository extends JpaRepository<DaySchedule, Long> {

    List<DaySchedule> findBySchedule_Id (Long scheduleId);

    List<DaySchedule> findBySchedule (Schedule schedule);

    Optional<DaySchedule> findByHopeDate (LocalDate date);
}
