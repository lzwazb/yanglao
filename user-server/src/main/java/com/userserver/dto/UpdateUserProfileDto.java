package com.userserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新用户个人信息DTO")
public class UpdateUserProfileDto {

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "头像URL")
    private String avatar;
}


