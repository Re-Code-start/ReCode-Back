package com.example.recode.repository;

import com.example.recode.domain.Answer;
import com.example.recode.domain.Problem;
import com.example.recode.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    boolean existsByProblemAndUser(Problem problem, Users user);

    Optional<Answer> findByProblemAndUser(Problem problem, Users user);

}
