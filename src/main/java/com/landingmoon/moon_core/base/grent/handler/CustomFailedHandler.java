package com.landingmoon.moon_core.base.grent.handler;

import com.landingmoon.moon_core.base.exception.BadCaptchaException;
import com.landingmoon.moon_core.base.exception.BadMethodException;
import com.landingmoon.moon_core.base.exception.CaptchaExpireException;
import com.landingmoon.moon_core.base.exception.CaptchaIsNullException;
import com.landingmoon.moon_core.base.response.Response;
import com.landingmoon.moon_core.base.response.ResultBody;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author LandingMoon
 * @Date 2022/9/14 15:44
 */
@Component
public class CustomFailedHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResultBody body = null;
        if (exception instanceof UsernameNotFoundException) body = ResultBody.no(HttpStatus.FORBIDDEN, "找不到用户", null);
        else if (exception instanceof BadCredentialsException) body = ResultBody.no(HttpStatus.FORBIDDEN, "错误的密码", null);
        else if (exception instanceof BadCaptchaException) body = ResultBody.no(HttpStatus.FORBIDDEN, "错误的验证码", null);
        else if (exception instanceof BadMethodException) body = ResultBody.no(HttpStatus.FORBIDDEN, "错误的请求方式", null);
        else if (exception instanceof CaptchaExpireException) body = ResultBody.no(HttpStatus.FORBIDDEN, "验证码已过期", null);
        else if (exception instanceof CaptchaIsNullException) body = ResultBody.no(HttpStatus.FORBIDDEN, "验证码不能为空", null);
        Response.stream(body);
    }
}
