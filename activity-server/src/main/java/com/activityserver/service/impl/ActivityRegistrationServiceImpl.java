package com.activityserver.service.impl;

import com.activityserver.entity.Activity;
import com.activityserver.entity.ActivityRegistration;
import com.activityserver.mapper.ActivityMapper;
import com.activityserver.mapper.ActivityRegistrationMapper;
import com.activityserver.service.ActivityRegistrationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ActivityRegistrationServiceImpl extends ServiceImpl<ActivityRegistrationMapper, ActivityRegistration> implements ActivityRegistrationService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityRegistrationMapper activityRegistrationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(Long activityId, Long userId, String userName) {
        // 1. 检查活动是否存在且状态正常
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new RuntimeException("活动不存在");
        }
        if (activity.getStatus() != 0 && activity.getStatus() != 1) {
            throw new RuntimeException("活动已结束或取消，无法报名");
        }
        if (activity.getEndTime() != null && activity.getEndTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("活动已结束");
        }

        // 2. 检查是否已报名
        LambdaQueryWrapper<ActivityRegistration> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityRegistration::getActivityId, activityId)
                .eq(ActivityRegistration::getUserId, userId)
                .eq(ActivityRegistration::getStatus, 1);
        Long count = activityRegistrationMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new RuntimeException("您已经报名过该活动");
        }

        // 3. 检查人数限制
        if (activity.getMaxPeople() > 0) {
            LambdaQueryWrapper<ActivityRegistration> countWrapper = new LambdaQueryWrapper<>();
            countWrapper.eq(ActivityRegistration::getActivityId, activityId)
                    .eq(ActivityRegistration::getStatus, 1);
            Long currentCount = activityRegistrationMapper.selectCount(countWrapper);
            if (currentCount >= activity.getMaxPeople()) {
                throw new RuntimeException("活动名额已满");
            }
        }

        // 4. 创建报名记录
        ActivityRegistration registration = new ActivityRegistration();
        registration.setActivityId(activityId);
        registration.setUserId(userId);
        registration.setUserName(userName);
        registration.setStatus(1);
        registration.setCreateTime(LocalDateTime.now());
        
        return activityRegistrationMapper.insert(registration) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(Long activityId, Long userId) {
        LambdaQueryWrapper<ActivityRegistration> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityRegistration::getActivityId, activityId)
                .eq(ActivityRegistration::getUserId, userId)
                .eq(ActivityRegistration::getStatus, 1);
        
        ActivityRegistration registration = activityRegistrationMapper.selectOne(queryWrapper);
        if (registration == null) {
            throw new RuntimeException("未找到有效的报名记录");
        }

        // 检查活动是否已开始（可选：活动开始前多久不能取消）
        Activity activity = activityMapper.selectById(activityId);
        if (activity != null && activity.getStartTime().isBefore(LocalDateTime.now())) {
             throw new RuntimeException("活动已开始，无法取消报名");
        }

        registration.setStatus(0); // 0-已取消
        return activityRegistrationMapper.updateById(registration) > 0;
    }
}
