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
import com.loginserver.entity.dto.RegisterAdministratorDto;
import com.loginserver.entity.dto.RegisterEmployeeDto;
import com.loginserver.entity.dto.RegisterFamilyDto;
import com.loginserver.entity.dto.RegisterUserDto;
import com.loginserver.service.RegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class RegisterServiceImpl implements RegisterService {
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private AdministratorDao administratorDao;
    
    @Autowired
    private EmployeeDao employeeDao;
    
    @Autowired
    private FamilyDao familyDao;
    
    @Override
    public User registerUser(RegisterUserDto registerUserDto) {
        // 验证必填字段
        if (!StringUtils.hasText(registerUserDto.getUsername()) || 
            !StringUtils.hasText(registerUserDto.getPassword())) {
            throw new RuntimeException("用户名和密码不能为空");
        }
        
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, registerUserDto.getUsername());
        User existingUser = userDao.selectOne(userQueryWrapper);
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 创建新用户
        User user = new User();
        BeanUtils.copyProperties(registerUserDto, user);
        user.setStatus(0); // 0-正常状态
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        // 保存到数据库
        userDao.insert(user);
        
        return user;
    }
    
    @Override
    public Administrator registerAdministrator(RegisterAdministratorDto registerAdministratorDto) {
        // 验证必填字段
        if (!StringUtils.hasText(registerAdministratorDto.getUsername()) || 
            !StringUtils.hasText(registerAdministratorDto.getPassword())) {
            throw new RuntimeException("用户名和密码不能为空");
        }
        
        // 检查用户名是否已存在
        LambdaQueryWrapper<Administrator> adminQueryWrapper = new LambdaQueryWrapper<>();
        adminQueryWrapper.eq(Administrator::getUsername, registerAdministratorDto.getUsername());
        Administrator existingAdmin = administratorDao.selectOne(adminQueryWrapper);
        if (existingAdmin != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 创建新管理员
        Administrator administrator = new Administrator();
        BeanUtils.copyProperties(registerAdministratorDto, administrator);
        administrator.setStatus(0); // 0-正常状态
        
        // 保存到数据库
        administratorDao.insert(administrator);
        
        return administrator;
    }
    
    @Override
    public Employee registerEmployee(RegisterEmployeeDto registerEmployeeDto) {
        // 验证必填字段
        if (!StringUtils.hasText(registerEmployeeDto.getUsername()) || 
            !StringUtils.hasText(registerEmployeeDto.getPassword())) {
            throw new RuntimeException("用户名和密码不能为空");
        }
        
        // 检查用户名是否已存在
        LambdaQueryWrapper<Employee> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper.eq(Employee::getUsername, registerEmployeeDto.getUsername());
        Employee existingEmployee = employeeDao.selectOne(employeeQueryWrapper);
        if (existingEmployee != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 创建新员工
        Employee employee = new Employee();
        BeanUtils.copyProperties(registerEmployeeDto, employee);
        employee.setStatus(0); // 0-在职状态
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        
        // 保存到数据库
        employeeDao.insert(employee);
        
        return employee;
    }
    
    @Override
    public Family registerFamily(RegisterFamilyDto registerFamilyDto) {
        // 验证必填字段
        if (!StringUtils.hasText(registerFamilyDto.getUsername()) || 
            !StringUtils.hasText(registerFamilyDto.getPassword())) {
            throw new RuntimeException("用户名和密码不能为空");
        }
        
        // 检查用户名是否已存在
        LambdaQueryWrapper<Family> familyQueryWrapper = new LambdaQueryWrapper<>();
        familyQueryWrapper.eq(Family::getUsername, registerFamilyDto.getUsername());
        Family existingFamily = familyDao.selectOne(familyQueryWrapper);
        if (existingFamily != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 创建新家人
        Family family = new Family();
        BeanUtils.copyProperties(registerFamilyDto, family);
        family.setStatus(0); // 0-正常状态
        family.setCreateTime(LocalDateTime.now());
        family.setUpdateTime(LocalDateTime.now());
        
        // 保存到数据库
        familyDao.insert(family);
        
        return family;
    }

    @Override
    public Long findFamilyIdByName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        LambdaQueryWrapper<Family> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Family::getName, name);
        // 假设姓名可能重复，这里只取第一个，或者根据业务需求调整
        Family family = familyDao.selectOne(queryWrapper);
        return family != null ? family.getId() : null;
    }
}
