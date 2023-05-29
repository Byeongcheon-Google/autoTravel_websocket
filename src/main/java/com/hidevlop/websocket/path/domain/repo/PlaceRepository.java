package com.hidevlop.websocket.path.domain.repo;


import com.hidevlop.websocket.path.domain.entity.Place;
import com.hidevlop.websocket.path.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {

    List<Place> findAllByHopeDateAndSchedule(LocalDate date, Schedule schedule);

    List<Place> findAllByHopeDateAndSchedule_Id(LocalDate date, Long scheduleId);
}
