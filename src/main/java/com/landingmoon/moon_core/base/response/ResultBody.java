package com.landingmoon.moon_core.base.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @Author LandingMoon
 * @Date 2022/8/28 23:52
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResultBody<T> {

    private Integer code;
    private String message;
    private T data;

    public static <T> ResultBody<T> ok(){
        return new ResultBody(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
    }

    public static <T> ResultBody<T> ok(String message, T data){
        return new ResultBody(HttpStatus.OK.value(), message, data);
    }

    public static <T> ResultBody<T> ok(T data){
        return new ResultBody(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static <T> ResultBody<T> no(){
        return new ResultBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
    }

    public static <T> ResultBody<T> no(HttpStatus code, String message, T data){
        return new ResultBody(code.value(), message, data);
    }

    public static <T> ResultBody<T> no(String message, T data){
        return new ResultBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, data);
    }

    public static <T> ResultBody<T> no(T data){
        return new ResultBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), data);
    }
}
