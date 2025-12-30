package com.userserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.userserver.dao.FamilyDao;
import com.userserver.dao.UserDao;
import com.userserver.dto.UpdateFamilyProfileDto;
import com.userserver.dto.UpdatePasswordDto;
import com.userserver.dto.UpdateUserProfileDto;
import com.userserver.entity.Family;
import com.userserver.entity.User;
import com.userserver.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FamilyDao familyDao;

    @Override
    public User updateUserProfile(UpdateUserProfileDto updateUserProfileDto) {
        // 查询用户是否存在
        User existingUser = userDao.selectById(updateUserProfileDto.getId());
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 只更新非空字段
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, updateUserProfileDto.getId());

        if (StringUtils.hasText(updateUserProfileDto.getRealName())) {
            updateWrapper.set(User::getRealName, updateUserProfileDto.getRealName());
        }
        if (StringUtils.hasText(updateUserProfileDto.getEmail())) {
            updateWrapper.set(User::getEmail, updateUserProfileDto.getEmail());
        }
        if (StringUtils.hasText(updateUserProfileDto.getPhone())) {
            updateWrapper.set(User::getPhone, updateUserProfileDto.getPhone());
        }
        if (StringUtils.hasText(updateUserProfileDto.getAvatar())) {
            updateWrapper.set(User::getAvatar, updateUserProfileDto.getAvatar());
        }
        updateWrapper.set(User::getUpdateTime, LocalDateTime.now());

        userDao.update(null, updateWrapper);

        // 返回更新后的用户信息
        return userDao.selectById(updateUserProfileDto.getId());
    }

    @Override
    public boolean updateUserPassword(UpdatePasswordDto updatePasswordDto) {
        // 查询用户
        User user = userDao.selectById(updatePasswordDto.getId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证原密码（此处为明文示例，实际项目应进行加密处理）
        if (!user.getPassword().equals(updatePasswordDto.getOldPassword())) {
            throw new RuntimeException("原密码错误");
        }

        // 更新密码
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, updatePasswordDto.getId())
                .set(User::getPassword, updatePasswordDto.getNewPassword())
                .set(User::getUpdateTime, LocalDateTime.now());

        return userDao.update(null, updateWrapper) > 0;
    }

    @Override
    public Family updateFamilyProfile(UpdateFamilyProfileDto updateFamilyProfileDto) {
        // 查询家人是否存在
        Family existingFamily = familyDao.selectById(updateFamilyProfileDto.getId());
        if (existingFamily == null) {
            throw new RuntimeException("家人不存在");
        }

        // 只更新非空字段
        LambdaUpdateWrapper<Family> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Family::getId, updateFamilyProfileDto.getId());

        if (StringUtils.hasText(updateFamilyProfileDto.getName())) {
            updateWrapper.set(Family::getName, updateFamilyProfileDto.getName());
        }
        if (StringUtils.hasText(updateFamilyProfileDto.getPhone())) {
            updateWrapper.set(Family::getPhone, updateFamilyProfileDto.getPhone());
        }
        if (StringUtils.hasText(updateFamilyProfileDto.getAddress())) {
            updateWrapper.set(Family::getAddress, updateFamilyProfileDto.getAddress());
        }
        updateWrapper.set(Family::getUpdateTime, LocalDateTime.now());

        familyDao.update(null, updateWrapper);

        // 返回更新后的家人信息
        return familyDao.selectById(updateFamilyProfileDto.getId());
    }

    @Override
    public boolean updateFamilyPassword(Integer familyId, String oldPassword, String newPassword) {
        // 查询家人
        Family family = familyDao.selectById(familyId);
        if (family == null) {
            throw new RuntimeException("家人不存在");
        }

        // 验证原密码
        if (!family.getPassword().equals(oldPassword)) {
            throw new RuntimeException("原密码错误");
        }

        // 更新密码
        LambdaUpdateWrapper<Family> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Family::getId, familyId)
                .set(Family::getPassword, newPassword)
                .set(Family::getUpdateTime, LocalDateTime.now());

        return familyDao.update(null, updateWrapper) > 0;
    }
}


