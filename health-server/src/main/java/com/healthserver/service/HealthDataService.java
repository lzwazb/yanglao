package com.healthserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthserver.entity.HealthData;

import java.util.List;

public interface HealthDataService extends IService<HealthData> {
    
    /**
     * 获取用户的健康数据列表
     * @param userId 用户ID
     * @return 健康数据列表
     */
    List<HealthData> getListByUserId(Long userId);
}
