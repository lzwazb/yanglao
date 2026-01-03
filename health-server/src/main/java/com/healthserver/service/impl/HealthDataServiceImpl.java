package com.healthserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthserver.entity.HealthData;
import com.healthserver.mapper.HealthDataMapper;
import com.healthserver.service.HealthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthDataServiceImpl extends ServiceImpl<HealthDataMapper, HealthData> implements HealthDataService {

    @Autowired
    private HealthDataMapper healthDataMapper;

    @Override
    public List<HealthData> getListByUserId(Long userId) {
        LambdaQueryWrapper<HealthData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HealthData::getUserId, userId);
        queryWrapper.orderByDesc(HealthData::getMeasureTime);
        return healthDataMapper.selectList(queryWrapper);
    }
}
