package com.healthserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.healthserver.entity.HealthData;
import com.healthserver.entity.HealthProfile;
import com.healthserver.service.HealthDataService;
import com.healthserver.service.HealthProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/health")
@Tag(name = "健康管理接口")
public class HealthController {

    @Autowired
    private HealthProfileService healthProfileService;

    @Autowired
    private HealthDataService healthDataService;

    // --- 健康档案接口 ---

    @Operation(summary = "获取健康档案")
    @GetMapping("/profile/{userId}")
    public HealthProfile getProfile(@PathVariable Long userId) {
        return healthProfileService.getByUserId(userId);
    }

    @Operation(summary = "保存/更新健康档案")
    @PostMapping("/profile/save")
    public boolean saveProfile(@RequestBody HealthProfile profile) {
        if (profile.getId() == null) {
            profile.setCreateTime(LocalDateTime.now());
        }
        profile.setUpdateTime(LocalDateTime.now());
        return healthProfileService.saveOrUpdate(profile);
    }

    // --- 健康数据接口 ---

    @Operation(summary = "获取健康数据列表")
    @GetMapping("/data/list")
    public List<HealthData> getDataList(@RequestParam Long userId) {
        return healthDataService.getListByUserId(userId);
    }

    @Operation(summary = "添加健康数据")
    @PostMapping("/data/add")
    public boolean addData(@RequestBody HealthData data) {
        data.setCreateTime(LocalDateTime.now());
        if (data.getMeasureTime() == null) {
            data.setMeasureTime(LocalDateTime.now());
        }
        return healthDataService.save(data);
    }

    @Operation(summary = "删除健康数据")
    @PostMapping("/data/delete/{id}")
    public boolean deleteData(@PathVariable Long id) {
        return healthDataService.removeById(id);
    }

    // --- 管理员接口 ---

    @Operation(summary = "分页查询健康档案(管理员)")
    @GetMapping("/admin/profile/page")
    public Page<HealthProfile> getProfilePage(@RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "10") int pageSize) {
        return healthProfileService.page(new Page<>(pageNum, pageSize));
    }

    @Operation(summary = "分页查询健康数据(管理员)")
    @GetMapping("/admin/data/page")
    public Page<HealthData> getDataPage(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(required = false) Long userId) {
        LambdaQueryWrapper<HealthData> queryWrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            queryWrapper.eq(HealthData::getUserId, userId);
        }
        queryWrapper.orderByDesc(HealthData::getMeasureTime);
        return healthDataService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}
