package com.healthserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("health_data")
@Schema(name = "健康数据")
public class HealthData {

    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(description = "用户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @Schema(description = "收缩压(高压)")
    private Integer bloodPressureHigh;

    @Schema(description = "舒张压(低压)")
    private Integer bloodPressureLow;

    @Schema(description = "心率(次/分)")
    private Integer heartRate;

    @Schema(description = "血糖(mmol/L)")
    private Double bloodSugar;

    @Schema(description = "体温(℃)")
    private Double temperature;

    @Schema(description = "测量时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime measureTime;

    @Schema(description = "记录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
