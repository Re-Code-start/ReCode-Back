package com.example.recode.dto;

import com.example.recode.domain.User;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max = 10, message = "닉네임은 10글자 이하로 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&,./])[A-Za-z\\d@$!%*#?&,./]{8,15}$",
            message = "비밀번호는 8~15 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    private String email;

    @Builder
    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }

}
