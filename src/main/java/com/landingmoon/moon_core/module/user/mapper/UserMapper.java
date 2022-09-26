package com.landingmoon.moon_core.module.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landingmoon.moon_core.module.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author LandingMoon
 * @Date 2022/8/30 22:53
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<Map<String, Object>> selectUserInfo(@Param("username") String username);
}
