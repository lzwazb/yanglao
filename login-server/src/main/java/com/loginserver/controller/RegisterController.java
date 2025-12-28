package com.loginserver.controller;

import com.loginserver.entity.Administrator;
import com.loginserver.entity.Employee;
import com.loginserver.entity.Family;
import com.loginserver.entity.User;
import com.loginserver.entity.dto.RegisterAdministratorDto;
import com.loginserver.entity.dto.RegisterEmployeeDto;
import com.loginserver.entity.dto.RegisterFamilyDto;
import com.loginserver.entity.dto.RegisterUserDto;
import com.loginserver.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@Tag(name = "注册接口")
public class RegisterController {
    
    @Autowired
    private RegisterService registerService;
    
    @Operation(summary = "用户注册", description = "普通用户注册接口")
    @PostMapping("/user")
    public User registerUser(@RequestBody RegisterUserDto registerUserDto) {
        return registerService.registerUser(registerUserDto);
    }
    
    @Operation(summary = "管理员注册", description = "管理员注册接口")
    @PostMapping("/admin")
    public Administrator registerAdministrator(@RequestBody RegisterAdministratorDto registerAdministratorDto) {
        return registerService.registerAdministrator(registerAdministratorDto);
    }
    
    @Operation(summary = "员工注册", description = "员工注册接口")
    @PostMapping("/employee")
    public Employee registerEmployee(@RequestBody RegisterEmployeeDto registerEmployeeDto) {
        return registerService.registerEmployee(registerEmployeeDto);
    }
    
    @Operation(summary = "家人注册", description = "家人注册接口")
    @PostMapping("/family")
    public Family registerFamily(@RequestBody RegisterFamilyDto registerFamilyDto) {
        return registerService.registerFamily(registerFamilyDto);
    }
}

