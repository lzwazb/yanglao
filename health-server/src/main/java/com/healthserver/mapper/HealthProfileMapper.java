package com.healthserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthserver.entity.HealthProfile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HealthProfileMapper extends BaseMapper<HealthProfile> {
}
