package com.loginserver.service;

import com.loginserver.entity.Administrator;
import com.loginserver.entity.Family;
import com.loginserver.entity.User;
import com.loginserver.entity.dto.RegisterAdministratorDto;
import com.loginserver.entity.dto.RegisterFamilyDto;
import com.loginserver.entity.dto.RegisterUserDto;

public interface RegisterService {
    
    /**
     * 用户注册
     * @param registerUserDto 注册信息
     * @return 注册成功的用户信息
     */
    User registerUser(RegisterUserDto registerUserDto);
    
    /**
     * 管理员注册
     * @param registerAdministratorDto 注册信息
     * @return 注册成功的管理员信息
     */
    Administrator registerAdministrator(RegisterAdministratorDto registerAdministratorDto);
    
    /**
     * 家人注册
     * @param registerFamilyDto 注册信息
     * @return 注册成功的家人信息
     */
    Family registerFamily(RegisterFamilyDto registerFamilyDto);

    /**
     * 根据姓名查找家属ID
     * @param name 家属姓名
     * @return 家属ID，如果未找到返回null
     */
    Long findFamilyIdByName(String name);
}
