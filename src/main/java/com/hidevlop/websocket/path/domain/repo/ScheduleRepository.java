package com.hidevlop.websocket.path.domain.repo;


import com.hidevlop.websocket.path.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

    Optional<Schedule> findByRoomId(String roomId);

    List<Schedule> findAllByUsername(String username);

    Optional<Schedule> findByUsernameAndId(String username, Long scheduleId);

    @Transactional
    void deleteById(Long scheduleId);
    Boolean existsByRoomId(String roomId);
}