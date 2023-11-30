package com.example.recode.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    private UserDto createUserDto(String nickname, String password, String email) {
        UserDto reqDto = new UserDto();
        reqDto.setNickname(nickname);
        reqDto.setPassword(password);
        reqDto.setEmail(email);
        return reqDto;
    }
    @Test
    public void nicknameValidation_null() {
        // Given
        UserDto userDto1 = createUserDto(null, "test123!", "test@example.com");
        UserDto userDto2 = createUserDto("", "test123!", "test@example.com");

        Set<ConstraintViolation<UserDto>> violations1 = validator.validate(userDto1);
        Set<ConstraintViolation<UserDto>> violations2 = validator.validate(userDto2);

        String errorMessage1 = violations1.iterator().next().getMessage();
        String errorMessage2 = violations2.iterator().next().getMessage();

        assertEquals("닉네임을 입력해주세요.", errorMessage1);
        assertEquals("닉네임을 입력해주세요.", errorMessage2);
    }

    @Test
    public void nicknameValidation_overLength() {
        UserDto userDto = createUserDto("12345678911", "test123!", "test@example.com");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        String errorMessage = violations.iterator().next().getMessage();

        assertEquals("닉네임은 10글자 이하로 입력해주세요.", errorMessage);
    }

    @Test
    public void passwordValidation_null() {
        UserDto userDto = createUserDto("test", null, "test@example.com" );

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        String errorMessage = violations.iterator().next().getMessage();

        assertEquals("비밀번호를 입력해주세요.", errorMessage);
    }

    @Test
    public void passwordValidation_length() {
        UserDto userDto1 = createUserDto("test", "1q,", "test@example.com");
        UserDto userDto2 = createUserDto("test", "1q2w3e4r5t6y7u,./", "test@example.com");

        Set<ConstraintViolation<UserDto>> violations1 = validator.validate(userDto1);
        Set<ConstraintViolation<UserDto>> violations2 = validator.validate(userDto2);

        String errorMessage1 = violations1.iterator().next().getMessage();
        String errorMessage2 = violations2.iterator().next().getMessage();

        assertEquals("비밀번호는 8~15 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.", errorMessage1);
        assertEquals("비밀번호는 8~15 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.", errorMessage2);
    }

    @Test
    public void passwordValidation_noneAlphabet() {
        UserDto userDto = createUserDto("test", "12345,./", "test@example.com" );

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        String errorMessage = violations.iterator().next().getMessage();

        assertEquals("비밀번호는 8~15 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.", errorMessage);
    }

    @Test
    public void passwordValidation_noneNum() {
        UserDto userDto = createUserDto("test", "qwert,./", "test@example.com" );

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        String errorMessage = violations.iterator().next().getMessage();

        assertEquals("비밀번호는 8~15 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.", errorMessage);
    }

    @Test
    public void passwordValidation_noneMark() {
        UserDto userDto = createUserDto("test", "1q2w3e4r", "test@example.com" );

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        String errorMessage = violations.iterator().next().getMessage();

        assertEquals("비밀번호는 8~15 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.", errorMessage);
    }

    @Test
    public void success() {
        UserDto userDto = createUserDto("test", "1q2w3e,./", "test@example.com" );

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(0, violations.size());
    }
}