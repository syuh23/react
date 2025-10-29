package org.example.reactstudy.service;

import lombok.RequiredArgsConstructor;
import org.example.reactstudy.dto.AccessTokenDto;
import org.example.reactstudy.dto.LoginDto;
import org.example.reactstudy.dto.SignUpDto;
import org.example.reactstudy.entity.User;
import org.example.reactstudy.repository.UserRepository;
import org.example.reactstudy.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public void signup(SignUpDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        userRepository.save(user);
    }

    public AccessTokenDto login(LoginDto dto) {
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isEmpty()) {
            throw new RuntimeException("없는 회원입니다.");
        }

        if (!user.get().getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("없는 회원입니다.");
        }

        String accessToken = jwtUtil.generateAccessToken(user.get().getId(), null);

        return AccessTokenDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
