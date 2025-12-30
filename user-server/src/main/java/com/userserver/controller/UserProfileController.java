package com.userserver.controller;

import com.userserver.dto.UpdateFamilyProfileDto;
import com.userserver.dto.UpdatePasswordDto;
import com.userserver.dto.UpdateUserProfileDto;
import com.userserver.entity.Family;
import com.userserver.entity.User;
import com.userserver.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/profile")
@Tag(name = "个人中心接口")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Operation(summary = "更新用户个人信息", description = "普通用户修改自己的个人信息")
    @PostMapping("/update")
    public User updateUserProfile(@RequestBody UpdateUserProfileDto updateUserProfileDto) {
        return userProfileService.updateUserProfile(updateUserProfileDto);
    }

    @Operation(summary = "修改用户密码", description = "普通用户修改自己的密码")
    @PostMapping("/password")
    public boolean updateUserPassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        return userProfileService.updateUserPassword(updatePasswordDto);
    }

    @Operation(summary = "更新家人个人信息", description = "家人修改自己的个人信息")
    @PostMapping("/family/update")
    public Family updateFamilyProfile(@RequestBody UpdateFamilyProfileDto updateFamilyProfileDto) {
        return userProfileService.updateFamilyProfile(updateFamilyProfileDto);
    }

    @Operation(summary = "修改家人密码", description = "家人修改自己的密码")
    @PostMapping("/family/password")
    public boolean updateFamilyPassword(@RequestParam Integer familyId,
                                        @RequestParam String oldPassword,
                                        @RequestParam String newPassword) {
        return userProfileService.updateFamilyPassword(familyId, oldPassword, newPassword);
    }
}


