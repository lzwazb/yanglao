package com.yanglao.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("service_item")
@Schema(name = "服务项目")
public class ServiceItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "服务名称")
    private String name;

    @Schema(description = "服务描述")
    private String description;

    @Schema(description = "图标标识")
    private String icon;

    @Schema(description = "参考价格")
    private BigDecimal price;

    @Schema(description = "状态: 0-下架, 1-上架")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
