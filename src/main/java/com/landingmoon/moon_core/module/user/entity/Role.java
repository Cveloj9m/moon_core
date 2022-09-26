package com.landingmoon.moon_core.module.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("role")
public class Role implements Serializable {

    @TableId
    private String id;

    private String name;

    private Boolean enabled;

    private Boolean deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}