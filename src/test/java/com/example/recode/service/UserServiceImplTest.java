package com.example.recode.service;

import com.example.recode.domain.MembershipLevel;
import com.example.recode.domain.Users;
import com.example.recode.dto.UserDto;
import com.example.recode.repository.UserRepository;
import com.example.recode.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

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
        when(userRepository.findByNickname(nickname)).thenReturn(Optional.of(new Users()));

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
        UserDto reqDto = createUserDto("test", "test123!", "test@example.com");
        when(userRepository.save(any(Users.class))).thenThrow(new RuntimeException("회원 가입 중 에러가 발생하였습니다."));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.join(reqDto);
        });

        assertEquals("회원 가입 중 에러가 발생하였습니다.", exception.getMessage());
        verify(userRepository).save(any(Users.class));
    }

    @Test
    void join_success() {
        UserDto reqDto = createUserDto("test", "test123!", "test@example.com");

        when(passwordEncoder.encode(reqDto.getPassword())).thenReturn("encodedPassword");

        Users savedUser = new Users();
        savedUser.setMembershipLevel(MembershipLevel.BASIC);

        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        boolean result = userService.join(reqDto);

        assertTrue(result);
        verify(userRepository).save(argThat(user -> user.getMembershipLevel() == MembershipLevel.BASIC));
    }

    @Test
    void login_UnknownNickname() {
        String nickname = "unknown";
        String password = "1q2w3e,./";

        when(userRepository.findByNickname(nickname)).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.login(nickname, password),
                "가입 되지 않은 닉네임입니다."
        );
    }

    @Test
    void login_Incorrect_password() {
        String nickname = "test";
        String password = "wrongPassword";
        String originPassword = "1q2w3e,./";

        Users user = new Users();
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(originPassword));

        when(userRepository.findByNickname(nickname)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq(password), eq(user.getPassword()))).thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.login(nickname, password),
                "닉네임 또는 비밀번호가 일치하지 않습니다."
        );
    }

    @Test
    void login_Success() {
        String nickname = "test";
        String password = "1q2w3e,./";
        String token = "jwtToken";

        Users user = new Users();
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(password));

        when(userRepository.findByNickname(nickname)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq(password), eq(user.getPassword()))).thenReturn(true);
        when(jwtTokenProvider.createToken(nickname, user.getMembershipLevel())).thenReturn(token);

        String result = userService.login(nickname, password);

        assertEquals(token, result);
    }

}