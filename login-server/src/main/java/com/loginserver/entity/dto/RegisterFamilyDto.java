package com.loginserver.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "家人注册DTO")
public class RegisterFamilyDto {
    
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    
    @Schema(description = "姓名")
    private String name;
    
    @Schema(description = "手机号")
    private String phone;
    
    @Schema(description = "地址")
    private String address;
}

