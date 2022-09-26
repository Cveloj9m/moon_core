package com.landingmoon.moon_core.module.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {

    @TableId
    private String id;

    private String username;

    private String nickname;

    private String password;

    private String phone;

    private String email;

    private Boolean enabled;

    private Boolean deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}