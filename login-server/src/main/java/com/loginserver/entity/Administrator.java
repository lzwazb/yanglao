package com.loginserver.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 账号密码作为唯一的登录标识符
 */
@Data
@Schema(name = "管理员")
public class Administrator {

    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 姓名
     */
    private String username;

    private String password;

    private String realName;

    private String phone;

    private String email;

    //用于表示逻辑删除  1 删除  0 正常
    private Integer status;

}
