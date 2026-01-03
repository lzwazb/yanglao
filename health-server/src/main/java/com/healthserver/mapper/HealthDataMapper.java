package com.healthserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthserver.entity.HealthData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HealthDataMapper extends BaseMapper<HealthData> {
}
