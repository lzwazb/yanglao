package com.administrator.controller;

import com.administrator.entity.dto.DashboardStatsDto;
import com.administrator.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("administrator/statistics")
@Tag(name = "统计接口")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @Operation(summary = "获取工作台统计数据")
    @GetMapping("/dashboard")
    public DashboardStatsDto getDashboardStats() {
        return statisticsService.getDashboardStats();
    }
}
