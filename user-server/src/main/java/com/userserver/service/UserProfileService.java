package com.userserver.service;

import com.userserver.entity.Family;
import com.userserver.entity.User;
import com.userserver.dto.UpdateFamilyProfileDto;
import com.userserver.dto.UpdatePasswordDto;
import com.userserver.dto.UpdateUserProfileDto;

public interface UserProfileService {

    /**
     * 更新用户个人信息
     * @param updateUserProfileDto 更新信息
     * @return 更新后的用户信息
     */
    User updateUserProfile(UpdateUserProfileDto updateUserProfileDto);

    /**
     * 修改用户密码
     * @param updatePasswordDto 密码信息
     * @return 是否成功
     */
    boolean updateUserPassword(UpdatePasswordDto updatePasswordDto);

    /**
     * 更新家人个人信息
     * @param updateFamilyProfileDto 更新信息
     * @return 更新后的家人信息
     */
    Family updateFamilyProfile(UpdateFamilyProfileDto updateFamilyProfileDto);

    /**
     * 修改家人密码
     * @param familyId 家人ID
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 是否成功
     */
    boolean updateFamilyPassword(Integer familyId, String oldPassword, String newPassword);
}


