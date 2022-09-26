package com.landingmoon.moon_core.base.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;

/**
 * jwt工具类
 */
public class JwtUtils {

    //密钥
    public static String SECRET = "landingMoon's moon_core";
    //Authorization

    //创建token
    //传入userid
    public static String createToken(String userId){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,24*60*60*7);
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256,SECRET);
        return builder.compact();
    }

    //校验jwt
    public static Claims parseToken(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }

    //判断token是否过期
    public static boolean judgeTokenExpiration(Date expiration){
        return expiration.before(new Date());
    }
}