package com.loginserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.loginserver.dao.AdministratorDao;
import com.loginserver.entity.Administrator;
import com.loginserver.entity.dto.AdministratorDto;
import com.loginserver.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    AdministratorDao administratorDao;

    @Override
    public Administrator adminLogin(AdministratorDto administratorDto) {
        LambdaQueryWrapper<Administrator> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Administrator::getUsername, administratorDto.getUsername());
        queryWrapper.eq(Administrator::getPassword, administratorDto.getPassword());
        return administratorDao.selectOne(queryWrapper);
    }
}
