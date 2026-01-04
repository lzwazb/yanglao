package com.userserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.userserver.dao.FamilyDao;
import com.userserver.dao.UserDao;
import com.userserver.entity.Family;
import com.userserver.entity.User;
import com.userserver.service.FamilyElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyElderlyServiceImpl implements FamilyElderlyService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FamilyDao familyDao;

    @Override
    public List<User> getElderlyByFamilyId(Long familyId) {
        // 验证家人是否存在
        Family family = familyDao.selectById(familyId);
        if (family == null) {
            throw new RuntimeException("家人不存在");
        }

        // 查询关联的老人（通过 familyId 关联）
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getFamilyId, familyId)
                .eq(User::getStatus, 0); // 只查询正常状态的老人

        return userDao.selectList(queryWrapper);
    }

    @Override
    public User getElderlyDetail(Long familyId, Long elderlyId) {
        // 验证家人是否存在
        Family family = familyDao.selectById(familyId);
        if (family == null) {
            throw new RuntimeException("家人不存在");
        }

        // 查询老人信息
        User elderly = userDao.selectById(elderlyId);
        if (elderly == null) {
            throw new RuntimeException("老人不存在");
        }

        // 验证该老人是否属于该家人
        if (!elderly.getFamilyId().equals(familyId)) {
            throw new RuntimeException("无权访问该老人信息");
        }

        return elderly;
    }
}
