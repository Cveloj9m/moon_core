package com.landingmoon.moon_core.base.grent.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author LandingMoon
 * @Date 2022/9/2 15:17
 */
@Slf4j
@Component("cah")
public class CustomAuthorizationHandler implements SecurityExpressionOperations {

    @Override
    public Authentication getAuthentication() {
        return null;
    }

    @Override
    public boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> list = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        return list.stream().map(SimpleGrantedAuthority::getAuthority).filter(authority::equals).findAny().isPresent();
    }

    public boolean url(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        log.info("==> 请求者的IP："+request.getRemoteAddr());
        System.err.println(request.getRequestURI());
        return true;
    }

    @Override
    public boolean hasAnyAuthority(String... authorities) {
        return false;
    }

    @Override
    public boolean hasRole(String role) {
        return false;
    }

    @Override
    public boolean hasAnyRole(String... roles) {
        return false;
    }

    @Override
    public boolean permitAll() {
        return false;
    }

    @Override
    public boolean denyAll() {
        return false;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public boolean isFullyAuthenticated() {
        return false;
    }

    @Override
    public boolean hasPermission(Object target, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Object targetId, String targetType, Object permission) {
        return false;
    }
}
