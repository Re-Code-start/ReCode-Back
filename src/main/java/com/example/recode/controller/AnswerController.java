package com.example.recode.controller;

import com.example.recode.dto.answer.AnswerAddRequestDto;
import com.example.recode.dto.answer.AnswerListDto;
import com.example.recode.dto.answer.AnswerResponseDto;
import com.example.recode.dto.answer.AnswerUpdateRequestDto;
import com.example.recode.dto.note.NoteResponseDto;
import com.example.recode.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/{answerId}")
    public AnswerResponseDto get(@PathVariable Long answerId) {
        return answerService.getAnswer(answerId);
    }

    @GetMapping("/list/{groupId}")
    public List<AnswerListDto> getList(@PathVariable Long groupId) {
        return answerService.getAnswerList(groupId);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody AnswerAddRequestDto dto) {
        answerService.addAnswer(dto);
        return ResponseEntity.ok("풀이 코드가 추가되었습니다.");
    }

    @PutMapping("/{answerId}")
    public ResponseEntity update(@PathVariable Long answerId, @RequestBody AnswerUpdateRequestDto dto) {
        answerService.updateAnswer(answerId, dto);
        return ResponseEntity.ok("풀이 코드가 수정되었습니다.");
    }

}
