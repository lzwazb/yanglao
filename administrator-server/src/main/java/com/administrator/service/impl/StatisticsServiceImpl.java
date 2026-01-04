package com.administrator.service.impl;

import com.administrator.client.ActivityServerClient;
import com.administrator.client.BusinessServerClient;
import com.administrator.dao.FamilyDao;
import com.administrator.dao.UserDao;
import com.administrator.entity.dto.DashboardStatsDto;
import com.administrator.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FamilyDao familyDao;

    @Autowired
    private BusinessServerClient businessServerClient;

    @Autowired
    private ActivityServerClient activityServerClient;

    @Override
    public DashboardStatsDto getDashboardStats() {
        DashboardStatsDto stats = new DashboardStatsDto();
        
        // 1. 获取用户总数
        stats.setUserCount(userDao.selectCount(null));
        
        // 2. 获取家人总数
        stats.setFamilyCount(familyDao.selectCount(null));
        
        // 3. 获取服务订单总数 (远程调用)
        try {
            stats.setServiceOrderCount(businessServerClient.getServiceOrderCount());
        } catch (Exception e) {
            System.err.println("获取服务订单总数失败: " + e.getMessage());
            stats.setServiceOrderCount(0L);
        }
        
        // 4. 获取活动总数 (远程调用)
        try {
            stats.setActivityCount(activityServerClient.getActivityCount());
        } catch (Exception e) {
            System.err.println("获取活动总数失败: " + e.getMessage());
            stats.setActivityCount(0L);
        }
        
        return stats;
    }
}
