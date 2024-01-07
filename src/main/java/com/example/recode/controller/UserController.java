package com.example.recode.controller;

import com.example.recode.dto.UserDto;
import com.example.recode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/join/exists")
    public ResponseEntity<Boolean> checkNickNameDuplicate(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(userService.nicknameCheck(nickname));
    }

    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@Valid @RequestBody UserDto user) {
        return ResponseEntity.ok(userService.join(user));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass().getSimpleName() + ": " + e.getMessage());
    }
}
