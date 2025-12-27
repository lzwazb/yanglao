package com.loginserver.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Schema(name = "用户")
public class User {

    private Integer id;

    private String username;

    private String password;

    private String realName;

    private String email;

    private String phone;

    private String avatar; //用户头像URLx

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer familyId;



}
