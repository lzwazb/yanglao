package com.yanglao.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("service_order")
@Schema(name = "服务订单")
public class ServiceOrder {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "服务项目ID")
    private Long serviceId;

    @Schema(description = "服务名称快照")
    private String serviceName;

    @Schema(description = "服务类型: CHECK_IN(入住), HOME(上门)")
    private String type;

    @Schema(description = "地址信息(房间号/单元号等)")
    private String addressInfo;

    @Schema(description = "预约服务时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookTime;

    @Schema(description = "状态: PENDING(待确认), CONFIRMED(已确认), COMPLETED(已完成), CANCELLED(已取消)")
    private String status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
