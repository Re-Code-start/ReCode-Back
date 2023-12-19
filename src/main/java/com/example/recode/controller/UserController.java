package com.example.recode.controller;

import com.example.recode.dto.UserDto;
import com.example.recode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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

/*    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody Map<String, String> loginForm) {
        JwtToken token = userService.login(loginForm.get("nickname"), loginForm.get("password"));
        return ResponseEntity.ok(token);
    }

 */

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> userInfo) {
        System.out.println("test");
        String nickname = userInfo.get("nickname");
        String password = userInfo.get("password");
        System.out.println(nickname+","+password);
        String tokenInfo = userService.login(nickname, password);
        System.out.println(tokenInfo);
        return ResponseEntity.ok().body(tokenInfo);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass().getSimpleName() + ": " + e.getMessage());
    }
}
