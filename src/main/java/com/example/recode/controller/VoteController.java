package com.example.recode.controller;


import com.example.recode.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/{answerId}")
    public ResponseEntity add(@PathVariable Long answerId) {
        voteService.add(answerId);
        return ResponseEntity.ok("투표에 성공하였습니다.");
    }

}
