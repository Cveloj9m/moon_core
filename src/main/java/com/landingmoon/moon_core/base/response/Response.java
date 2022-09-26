package com.landingmoon.moon_core.base.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Response {

    public static <T> void stream(T t) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = attributes.getResponse();
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter print = response.getWriter();
            print.write(new ObjectMapper().writeValueAsString(t));
            print.flush();
            print.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
