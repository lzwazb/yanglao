package com.loginserver.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "员工注册DTO")
public class RegisterEmployeeDto {
    
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    
    @Schema(description = "姓名")
    private String name;
    
    @Schema(description = "性别")
    private String gender;
    
    @Schema(description = "手机号")
    private String phone;
    
    @Schema(description = "部门")
    private String department;
    
    @Schema(description = "职位")
    private String position;
}

