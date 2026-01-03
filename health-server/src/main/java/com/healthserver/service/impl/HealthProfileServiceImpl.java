package com.healthserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthserver.entity.HealthProfile;
import com.healthserver.mapper.HealthProfileMapper;
import com.healthserver.service.HealthProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthProfileServiceImpl extends ServiceImpl<HealthProfileMapper, HealthProfile> implements HealthProfileService {

    @Autowired
    private HealthProfileMapper healthProfileMapper;

    @Override
    public HealthProfile getByUserId(Long userId) {
        LambdaQueryWrapper<HealthProfile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HealthProfile::getUserId, userId);
        return healthProfileMapper.selectOne(queryWrapper);
    }
}
