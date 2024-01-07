package com.example.recode.service;

import com.example.recode.dto.UserDto;

public interface UserService {
    boolean nicknameCheck(String nickname);

    boolean join(UserDto request);
}
