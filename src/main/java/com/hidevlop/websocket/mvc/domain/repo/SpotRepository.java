package com.hidevlop.websocket.mvc.domain.repo;


import com.hidevlop.websocket.mvc.domain.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot,Long> {

}
