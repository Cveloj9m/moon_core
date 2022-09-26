package com.landingmoon.moon_core.module.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author LandingMoon
 * @Date 2022/8/30 22:47
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("role_auth")
public class RoleAuth implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String roleId;

    private String authId;
}
