package com.example.recode.controller;

import com.example.recode.dto.answer.AnswerAddRequestDto;
import com.example.recode.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity add(@RequestBody AnswerAddRequestDto dto) {
        answerService.addAnswer(dto);
        return ResponseEntity.ok("풀이 코드가 추가되었습니다.");
    }

}
