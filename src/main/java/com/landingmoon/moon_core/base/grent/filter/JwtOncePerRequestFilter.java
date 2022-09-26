package com.landingmoon.moon_core.base.grent.filter;

import com.landingmoon.moon_core.base.grent.constant.GrentConst;
import com.landingmoon.moon_core.base.grent.domain.Principals;
import com.landingmoon.moon_core.base.response.Response;
import com.landingmoon.moon_core.base.response.ResultBody;
import com.landingmoon.moon_core.base.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtOncePerRequestFilter extends OncePerRequestFilter {

    private final RedisTemplate redisTemplate;

    public JwtOncePerRequestFilter(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerToken = request.getHeader("Authorization");
        if (request.getRequestURI().contains(GrentConst.LOGIN_URL))
            return;
        if (!StringUtils.hasText(headerToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        Principals principals = null;
        try {
            Claims claims = JwtUtils.parseToken(headerToken);
            principals = (Principals) redisTemplate.opsForValue().get(GrentConst.TOKEN_KEY + claims.getSubject());
            if (ObjectUtils.isEmpty(principals)) {
                Response.stream(ResultBody.no(HttpStatus.FORBIDDEN, "用户已过期", null));
                throw new Exception("用户已过期");
            }
        }catch (Exception e){
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principals.getLoginUser(), null, principals.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}