package com.landingmoon.moon_core.module.user.controller;

import com.landingmoon.moon_core.base.response.ResultBody;
import com.landingmoon.moon_core.module.user.entity.User;
import com.landingmoon.moon_core.module.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LandingMoon
 * @Date 2022/8/28 23:50
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResultBody addUser(@RequestBody User user){
        try {
            userService.addUser(user);
            return ResultBody.ok();
        }catch (Exception e){
            e.printStackTrace();
            return ResultBody.no();
        }
    }

}
