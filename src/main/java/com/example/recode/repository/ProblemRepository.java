package com.example.recode.repository;

import com.example.recode.domain.Challenge;
import com.example.recode.domain.Problem;
import com.example.recode.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    int countByChallengeAndUser(Challenge challenge, Users user);

}
