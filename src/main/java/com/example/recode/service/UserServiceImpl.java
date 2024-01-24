package com.example.recode.service;

import com.example.recode.domain.Users;
import com.example.recode.dto.UserDto;
import com.example.recode.repository.UserRepository;
import com.example.recode.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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

        Users user = reqDto.toEntity();
        try{
            userRepository.save(user);
            return true;
        }catch(Exception e){
            throw new RuntimeException("회원 가입 중 에러가 발생하였습니다.", e);
        }
    }

    @Transactional
    public String login(String nickname, String password) {
        Users user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 닉네임입니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("닉네임 또는 비밀번호가 일치하지 않습니다.");
        }

        return jwtTokenProvider.createToken(user.getNickname(), user.getMembershipLevel());
    }

    @Override
    public Users getUser(String userId) {
        return null;
    }

}
