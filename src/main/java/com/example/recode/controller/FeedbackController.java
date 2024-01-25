package com.example.recode.controller;

import com.example.recode.dto.feedback.FeedbackAddDto;
import com.example.recode.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/answer")
    public ResponseEntity addAnswerFeedback(@RequestBody FeedbackAddDto dto) {
        feedbackService.addAnswerFeedback(dto);
        return ResponseEntity.ok("피드백이 추가되었습니다.");
    }

}
