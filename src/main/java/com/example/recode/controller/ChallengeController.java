package com.example.recode.controller;

import com.example.recode.dto.challenge.ChallengeResponseDto;
import com.example.recode.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping("/{groupId}/upcoming")
    public ChallengeResponseDto getUpcomingChallenge(@PathVariable Long groupId) {
        return challengeService.getUpcomingChallenge(groupId);
    }

    @GetMapping("/{groupId}/last")
    public List<ChallengeResponseDto> getLastChallenges(@PathVariable Long groupId, @RequestParam int pageNumber) {
        return challengeService.getLastChallenges(groupId, pageNumber);
    }

    @PatchMapping("/close/{challengeId}")
    public ResponseEntity closeFeedbackVote(@PathVariable Long challengeId) {
        challengeService.closeFeedbackVote(challengeId);
        return ResponseEntity.ok("피드백 및 투표가 종료되었습니다.");
    }

}
