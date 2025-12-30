package com.userserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新家人个人信息DTO")
public class UpdateFamilyProfileDto {

    @Schema(description = "家人ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "地址")
    private String address;
}


