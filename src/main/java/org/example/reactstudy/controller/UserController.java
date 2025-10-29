package org.example.reactstudy.controller;

import lombok.RequiredArgsConstructor;
import org.example.reactstudy.dto.AccessTokenDto;
import org.example.reactstudy.dto.LoginDto;
import org.example.reactstudy.dto.SignUpDto;
import org.example.reactstudy.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public void signup(@RequestBody SignUpDto dto) {
        userService.signup(dto);
    }

    // 로그인
    @PostMapping("/login")
    public AccessTokenDto login(@RequestBody LoginDto dto) {
        return userService.login(dto);
    }

}
