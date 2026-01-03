package com.healthserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthserver.entity.HealthProfile;

public interface HealthProfileService extends IService<HealthProfile> {
    
    /**
     * 根据用户ID获取健康档案
     * @param userId 用户ID
     * @return 健康档案
     */
    HealthProfile getByUserId(Long userId);
}
