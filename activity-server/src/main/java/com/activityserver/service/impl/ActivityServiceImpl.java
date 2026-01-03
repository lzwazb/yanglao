package com.activityserver.service.impl;

import com.activityserver.entity.Activity;
import com.activityserver.mapper.ActivityMapper;
import com.activityserver.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
}
