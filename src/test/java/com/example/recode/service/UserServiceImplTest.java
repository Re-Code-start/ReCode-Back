package com.example.recode.service;

import com.example.recode.domain.User;
import com.example.recode.dto.UserDto;
import com.example.recode.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void nicknameCheck_notExist() {
        String nickname = "test";
        when(userRepository.findByNickname(nickname)).thenReturn(Optional.empty());

        boolean result = userService.nicknameCheck(nickname);

        assertFalse(result);

        verify(userRepository).findByNickname(nickname);
    }

    @Test
    void nicknameCheck_Exist() {
        String nickname = "test";
        when(userRepository.findByNickname(nickname)).thenReturn(Optional.of(new User()));

        boolean result = userService.nicknameCheck(nickname);

        assertTrue(result);

        verify(userRepository).findByNickname(nickname);
    }

    @Test
    void nicknameCheck_null() {
        String nickname = "";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.nicknameCheck(nickname);
        });

        assertEquals("닉네임을 입력해주세요.", exception.getMessage());
    }

    private UserDto createUserDto(String nickname, String password, String email) {
        UserDto reqDto = new UserDto();
        reqDto.setNickname(nickname);
        reqDto.setPassword(password);
        reqDto.setEmail(email);
        return reqDto;
    }
    @Test
    void join_failure() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder());

        UserDto reqDto = createUserDto("test", "test123!", "test@example.com");
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("회원 가입 중 에러가 발생하였습니다."));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.join(reqDto);
        });

        assertEquals("회원 가입 중 에러가 발생하였습니다.", exception.getMessage());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void join_success() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder());

        UserDto reqDto = createUserDto("test", "test123!", "test@example.com");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        boolean result = userService.join(reqDto);

        assertTrue(result);
        verify(userRepository).save(any(User.class));
    }

}