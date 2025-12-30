package com.userserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "修改密码DTO")
public class UpdatePasswordDto {

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @Schema(description = "原密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
}


