package com.example.recode.service;

import com.example.recode.domain.Users;
import com.example.recode.dto.UserDto;
import com.example.recode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean  nicknameCheck(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("닉네임을 입력해주세요.");
        }
        Optional<Users> optionalMember = userRepository.findByNickname(nickname);
        if (optionalMember.isEmpty())
            return false;
        else
            return true;
    }

    @Override
    public boolean join(UserDto reqDto) {
        reqDto.setPassword(passwordEncoder.encode(reqDto.getPassword()));

        Users users = reqDto.toEntity();
        try{
            userRepository.save(users);
            return true;
        }catch(Exception e){
            throw new RuntimeException("회원 가입 중 에러가 발생하였습니다.", e);
        }
    }
}
