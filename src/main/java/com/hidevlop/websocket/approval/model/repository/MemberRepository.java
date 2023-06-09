package com.hidevlop.websocket.approval.model.repository;


import com.hidevlop.websocket.approval.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member getByUsername(String username);

    Optional<Member> findByUsername(String username);


    boolean existsByUsername(String username);
}

