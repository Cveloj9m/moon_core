package com.landingmoon.moon_core.module.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("auth")
public class Auth implements Serializable {

    @TableId
    private String id;

    private String name;

    private String component;

    private String path;

    private String authority;

    private String url;

    private String icon;

    private Integer sort;

    private Integer type;

    private Long pid;

    private Boolean auth;

    private Boolean sided;

    private Boolean enabled;

    @TableLogic
    private Integer deleted;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<String> authorities;

    @TableField(exist = false)
    private List<Auth> children;
}

