package com.landingmoon.moon_core.base.grent.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author LandingMoon
 * @Date 2022/8/29 1:25
 */
@Data
@NoArgsConstructor
public class LoginUser {

    public LoginUser(String id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    private String id;

    private String username;

    private String password;

    private String captcha;

    private String token;

    private String uuid;
}
