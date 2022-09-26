package com.landingmoon.moon_core.base.grent.handler;

import com.landingmoon.moon_core.base.grent.constant.GrentConst;
import com.landingmoon.moon_core.base.grent.domain.LoginUser;
import com.landingmoon.moon_core.base.grent.domain.Principals;
import com.landingmoon.moon_core.base.response.Response;
import com.landingmoon.moon_core.base.response.ResultBody;
import com.landingmoon.moon_core.base.utils.JwtUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final RedisTemplate redisTemplate;

    public CustomSuccessHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Principals principals = (Principals) authentication.getPrincipal();
        LoginUser loginUser = principals.getLoginUser();
        String token = JwtUtils.createToken(loginUser.getId());
        redisTemplate.opsForValue().set(GrentConst.TOKEN_KEY + loginUser.getId(), principals, 2, TimeUnit.HOURS);
        Response.stream(ResultBody.ok(new HashMap() {{
            put("token", token);
            put("username", loginUser.getUsername());
            put("authorities", principals.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        }}));
    }
}