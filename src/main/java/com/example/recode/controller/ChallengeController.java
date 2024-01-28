package com.example.recode.controller;

import com.example.recode.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PatchMapping("/close/{challengeId}")
    public ResponseEntity closeFeedbackVote(@PathVariable Long challengeId) {
        challengeService.closeFeedbackVote(challengeId);
        return ResponseEntity.ok("피드백 및 투표가 종료되었습니다.");
    }

}
