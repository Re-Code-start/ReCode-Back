package com.example.recode.repository;

import com.example.recode.domain.Challenge;
import com.example.recode.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Page<Challenge> findAllByGroupAndEndDtBefore(Group group, LocalDateTime now, PageRequest pageRequest);

}
