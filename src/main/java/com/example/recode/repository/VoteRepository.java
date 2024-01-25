package com.example.recode.repository;

import com.example.recode.domain.Problem;
import com.example.recode.domain.Users;
import com.example.recode.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByVoterAndProblem(Users voter, Problem problem);

}
