package com.landingmoon.moon_core.module.user.service.impl;

import com.landingmoon.moon_core.module.user.entity.User;
import com.landingmoon.moon_core.module.user.mapper.UserMapper;
import com.landingmoon.moon_core.module.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author LandingMoon
 * @Date 2022/8/30 22:55
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public void addUser(User user) {
        userMapper.insert(User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .enabled(true)
                .deleted(false)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build());
    }

}
