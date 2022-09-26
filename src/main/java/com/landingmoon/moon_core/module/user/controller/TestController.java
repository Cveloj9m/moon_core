package com.landingmoon.moon_core.module.user.controller;

import com.landingmoon.moon_core.base.response.ResultBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LandingMoon
 * @Date 2022/8/29 23:31
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/api")
    @PreAuthorize("@cah.url()")
    public ResultBody api(){
        return ResultBody.ok();
    }
}
