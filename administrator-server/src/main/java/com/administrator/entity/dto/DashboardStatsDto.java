package com.administrator.entity.dto;

import lombok.Data;

@Data
public class DashboardStatsDto {
    private Long userCount;
    private Long familyCount;
    private Long serviceOrderCount;
    private Long activityCount;
}
