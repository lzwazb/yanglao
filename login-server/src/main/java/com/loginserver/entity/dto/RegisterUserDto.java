package com.loginserver.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户注册DTO")
public class RegisterUserDto {
    
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    
    @Schema(description = "真实姓名")
    private String realName;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "手机号")
    private String phone;
    
    @Schema(description = "家庭ID")
    private Long familyId;
}
