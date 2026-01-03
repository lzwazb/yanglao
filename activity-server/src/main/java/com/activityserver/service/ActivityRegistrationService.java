package com.activityserver.service;

import com.activityserver.entity.ActivityRegistration;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ActivityRegistrationService extends IService<ActivityRegistration> {
    
    /**
     * 用户报名活动
     * @param activityId 活动ID
     * @param userId 用户ID
     * @param userName 用户姓名
     * @return 是否报名成功
     */
    boolean register(Long activityId, Long userId, String userName);

    /**
     * 取消报名
     * @param activityId 活动ID
     * @param userId 用户ID
     * @return 是否取消成功
     */
    boolean cancel(Long activityId, Long userId);
}
