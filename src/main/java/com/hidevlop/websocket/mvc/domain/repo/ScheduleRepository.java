package com.hidevlop.websocket.mvc.domain.repo;


import com.hidevlop.websocket.mvc.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

    Optional<Schedule> findByRoomId(String roomId);
}