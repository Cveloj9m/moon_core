package com.landingmoon.moon_core.base.grent.handler;

import com.landingmoon.moon_core.base.grent.constant.GrentConst;
import com.landingmoon.moon_core.base.response.Response;
import com.landingmoon.moon_core.base.response.ResultBody;
import com.landingmoon.moon_core.base.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutHandler implements LogoutSuccessHandler {

    private final RedisTemplate redisTemplate;

    public CustomLogoutHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String headerToken = request.getHeader("Authorization");
        if(!StringUtils.hasText(headerToken)){
            Response.stream(ResultBody.no("未登录"));
        }
        try{
            Claims claims = JwtUtils.parseToken(headerToken);
            redisTemplate.delete(GrentConst.TOKEN_KEY + claims.getSubject());
            Response.stream(ResultBody.ok("登出成功"));
        }catch (Exception e){
            Response.stream(ResultBody.no("用户已失效"));
        }
    }

}
