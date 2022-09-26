package com.landingmoon.moon_core.base.grent.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landingmoon.moon_core.base.exception.BadCaptchaException;
import com.landingmoon.moon_core.base.exception.BadMethodException;
import com.landingmoon.moon_core.base.exception.CaptchaIsNullException;
import com.landingmoon.moon_core.base.grent.constant.GrentConst;
import com.landingmoon.moon_core.base.grent.domain.LoginUser;
import com.landingmoon.moon_core.base.grent.handler.CustomFailedHandler;
import com.landingmoon.moon_core.base.grent.handler.CustomSuccessHandler;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailedHandler customFailedHandler;
    private final RedisTemplate redisTemplate;

    public JsonAuthenticationFilter(RedisTemplate redisTemplate, AuthenticationManager authenticationManager, CustomSuccessHandler customSuccessHandler, CustomFailedHandler customFailedHandler) {
        super(GrentConst.LOGIN_URL, authenticationManager);
        this.redisTemplate = redisTemplate;
        this.customSuccessHandler = customSuccessHandler;
        this.customFailedHandler = customFailedHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (!request.getMethod().equals(HttpMethod.POST.name())) throw new BadMethodException("错误的请求方式");
        LoginUser loginUser = Optional.ofNullable(new ObjectMapper().readValue(request.getInputStream(), LoginUser.class)).filter(Objects::nonNull).orElseThrow(() -> new RuntimeException("未输入任何用户信息"));
        if (!StringUtils.hasText(loginUser.getUsername()) || !StringUtils.hasText(loginUser.getPassword()))
            throw new RuntimeException("用户名和密码不能为空");
        if (!StringUtils.hasText(loginUser.getCaptcha()) || !StringUtils.hasText(loginUser.getUuid()))
            throw new CaptchaIsNullException("");
        String value = (String) redisTemplate.opsForValue().get(GrentConst.CAPTCHA + loginUser.getUuid());
        if (!Objects.equals(value, loginUser.getCaptcha()))
            throw new BadCaptchaException("");
        redisTemplate.delete(GrentConst.CAPTCHA + loginUser.getUuid());
        return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        customSuccessHandler.onAuthenticationSuccess(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        customFailedHandler.onAuthenticationFailure(request, response, failed);
    }
}