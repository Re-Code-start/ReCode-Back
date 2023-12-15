package com.example.recode.service;

import com.example.recode.domain.Users;
import com.example.recode.dto.UserDto;

public interface UserService {
    boolean nicknameCheck(String nickname);

    boolean join(UserDto request);

    String login(String username, String password);

    Users getUser(String userId);
}

