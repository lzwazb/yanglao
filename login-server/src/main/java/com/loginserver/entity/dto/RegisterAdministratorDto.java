package com.loginserver.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理员注册DTO")
public class RegisterAdministratorDto {
    
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    
    @Schema(description = "真实姓名")
    private String realName;
    
    @Schema(description = "手机号")
    private String phone;
    
    @Schema(description = "邮箱")
    private String email;
}

