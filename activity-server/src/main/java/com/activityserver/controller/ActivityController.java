package com.activityserver.controller;

import com.activityserver.entity.Activity;
import com.activityserver.entity.ActivityRegistration;
import com.activityserver.service.ActivityRegistrationService;
import com.activityserver.service.ActivityService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/activity")
@Tag(name = "活动管理接口")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    // --- 管理员接口 ---

    @Operation(summary = "发布活动")
    @PostMapping("/add")
    public boolean addActivity(@RequestBody Activity activity) {
        activity.setCreateTime(LocalDateTime.now());
        activity.setUpdateTime(LocalDateTime.now());
        // 默认状态为0-未开始
        if (activity.getStatus() == null) {
            activity.setStatus(0);
        }
        return activityService.save(activity);
    }

    @Operation(summary = "更新活动")
    @PostMapping("/update")
    public boolean updateActivity(@RequestBody Activity activity) {
        activity.setUpdateTime(LocalDateTime.now());
        return activityService.updateById(activity);
    }

    @Operation(summary = "删除活动")
    @PostMapping("/delete/{id}")
    public boolean deleteActivity(@PathVariable Long id) {
        return activityService.removeById(id);
    }

    @Operation(summary = "分页查询活动(管理员)")
    @GetMapping("/page")
    public Page<Activity> getActivityPage(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) String title) {
        LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<>();
        if (title != null && !title.isEmpty()) {
            queryWrapper.like(Activity::getTitle, title);
        }
        queryWrapper.orderByDesc(Activity::getCreateTime);
        return activityService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    // --- 用户接口 ---

    @Operation(summary = "查询活动列表(用户端)")
    @GetMapping("/list")
    public List<Activity> getActivityList() {
        // 只查询未开始和进行中的活动
        LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Activity::getStatus, 0, 1);
        queryWrapper.orderByDesc(Activity::getStartTime);
        return activityService.list(queryWrapper);
    }

    @Operation(summary = "活动报名")
    @PostMapping("/register")
    public boolean register(@RequestParam Long activityId,
                            @RequestParam Long userId,
                            @RequestParam String userName) {
        return activityRegistrationService.register(activityId, userId, userName);
    }

    @Operation(summary = "取消报名")
    @PostMapping("/cancel")
    public boolean cancel(@RequestParam Long activityId,
                          @RequestParam Long userId) {
        return activityRegistrationService.cancel(activityId, userId);
    }

    @Operation(summary = "查询我的报名记录")
    @GetMapping("/my")
    public List<ActivityRegistration> getMyRegistrations(@RequestParam Long userId) {
        LambdaQueryWrapper<ActivityRegistration> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityRegistration::getUserId, userId);
        queryWrapper.orderByDesc(ActivityRegistration::getCreateTime);
        return activityRegistrationService.list(queryWrapper);
    }
    
    @Operation(summary = "查询活动详情")
    @GetMapping("/{id}")
    public Activity getActivity(@PathVariable Long id) {
        return activityService.getById(id);
    }

    @Operation(summary = "获取活动总数(内部调用)")
    @GetMapping("/count")
    public Long getActivityCount() {
        return activityService.count();
    }
}
