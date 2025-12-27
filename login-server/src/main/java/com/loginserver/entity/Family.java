package com.loginserver.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Schema(name = "家人")
public class Family {

    private Integer id;

    private String name;

    private String username;

    private String password;

    private String phone;

    private String address;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
