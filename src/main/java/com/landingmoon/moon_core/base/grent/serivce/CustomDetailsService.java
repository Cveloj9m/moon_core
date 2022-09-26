package com.landingmoon.moon_core.base.grent.serivce;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.landingmoon.moon_core.base.grent.constant.GrentConst;
import com.landingmoon.moon_core.base.grent.domain.LoginUser;
import com.landingmoon.moon_core.base.grent.domain.Principals;
import com.landingmoon.moon_core.base.response.ResultBody;
import com.landingmoon.moon_core.module.user.entity.User;
import com.landingmoon.moon_core.module.user.mapper.UserMapper;
import com.pig4cloud.captcha.ArithmeticCaptcha;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class CustomDetailsService implements UserDetailsService {

    private final UserMapper userMapper;
    private final RedisTemplate redisTemplate;

    public CustomDetailsService(UserMapper userMapper, RedisTemplate redisTemplate){
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username))).orElseThrow(() -> new UsernameNotFoundException("没有找到用户"));
        List<Map<String, Object>> list = userMapper.selectUserInfo(username);
        return new Principals(new LoginUser(user.getId(), user.getUsername(),user.getPassword()), list.stream().map(l->l.get("url").toString()).collect(Collectors.toList()));
    }

    @GetMapping("/captcha")
    public ResultBody captcha(String uuid){
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.setLen(3);  // 几位数运算，默认是两位
        captcha.supportAlgorithmSign(4); // 可设置支持的算法：2 表示只生成带加减法的公式
        captcha.setDifficulty(6); // 设置计算难度，参与计算的每一个整数的最大值
        redisTemplate.delete(GrentConst.CAPTCHA + uuid);
        String uniques = UUID.randomUUID().toString().replaceAll("-", "");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("uuid", uniques);
        map.put("captcha", captcha.toBase64());
        redisTemplate.opsForValue().set(GrentConst.CAPTCHA + uniques, captcha.text(), 300, TimeUnit.SECONDS);
        return ResultBody.ok(map);
    }
}