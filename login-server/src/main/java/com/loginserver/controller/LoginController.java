package com.loginserver.controller;

import com.loginserver.entity.Administrator;
import com.loginserver.entity.dto.AdministratorDto;
import com.loginserver.service.LoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "登录接口")
public class LoginController {

    @Autowired
    LoginService loginService;


    //管理员登录
    @PostMapping("/admin")
    public Administrator getAdmin(@RequestBody AdministratorDto administratorDto) {

        Administrator loginAdmin = loginService.adminLogin(administratorDto);

        return loginAdmin;
    }

    //用户登录


    //家人登录


    //员工登录
}
