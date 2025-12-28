package com.loginserver.service;

import com.loginserver.entity.Administrator;
import com.loginserver.entity.Employee;
import com.loginserver.entity.Family;
import com.loginserver.entity.User;
import com.loginserver.entity.dto.RegisterAdministratorDto;
import com.loginserver.entity.dto.RegisterEmployeeDto;
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
     * 员工注册
     * @param registerEmployeeDto 注册信息
     * @return 注册成功的员工信息
     */
    Employee registerEmployee(RegisterEmployeeDto registerEmployeeDto);
    
    /**
     * 家人注册
     * @param registerFamilyDto 注册信息
     * @return 注册成功的家人信息
     */
    Family registerFamily(RegisterFamilyDto registerFamilyDto);
}
