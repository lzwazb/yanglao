package com.loginserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.loginserver.dao.AdministratorDao;
import com.loginserver.dao.EmployeeDao;
import com.loginserver.dao.FamilyDao;
import com.loginserver.dao.UserDao;
import com.loginserver.entity.Administrator;
import com.loginserver.entity.Employee;
import com.loginserver.entity.Family;
import com.loginserver.entity.User;
import com.loginserver.entity.dto.AdministratorDto;
import com.loginserver.entity.dto.EmployeeDto;
import com.loginserver.entity.dto.FamilyDto;
import com.loginserver.entity.dto.UserDto;
import com.loginserver.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    AdministratorDao administratorDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    FamilyDao familyDao;

    @Autowired
    UserDao userDao;

    @Override
    public Administrator adminLogin(AdministratorDto administratorDto) {
        LambdaQueryWrapper<Administrator> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Administrator::getUsername, administratorDto.getUsername());
        queryWrapper.eq(Administrator::getPassword, administratorDto.getPassword());
        return administratorDao.selectOne(queryWrapper);
    }

    @Override
    public Employee employeeLogin(EmployeeDto employeeDto) {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employeeDto.getUsername());
        queryWrapper.eq(Employee::getPassword, employeeDto.getPassword());
        return employeeDao.selectOne(queryWrapper);
    }

    @Override
    public Family familyLogin(FamilyDto familyDto) {
        LambdaQueryWrapper<Administrator> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Administrator::getUsername, administratorDto.getUsername());
        queryWrapper.eq(Administrator::getPassword, administratorDto.getPassword());
        return administratorDao.selectOne(queryWrapper);
    }

    @Override
    public User userLogin(UserDto userDto) {
        LambdaQueryWrapper<Administrator> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Administrator::getUsername, administratorDto.getUsername());
        queryWrapper.eq(Administrator::getPassword, administratorDto.getPassword());
        return administratorDao.selectOne(queryWrapper);
    }
}
