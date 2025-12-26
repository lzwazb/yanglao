package com.administrator.service.impl;

import com.administrator.dao.AdministratorDao;
import com.administrator.entity.Administrator;
import com.administrator.entity.dto.AdministratorDto;
import com.administrator.service.AdministratorService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    AdministratorDao administratorDao;








    @Override
    public Administrator login(AdministratorDto administratorDto) {

        LambdaQueryWrapper<Administrator> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Administrator::getUsername, administratorDto.getUsername());
        queryWrapper.eq(Administrator::getPassword, administratorDto.getPassword());

        return administratorDao.selectOne(queryWrapper);
    }
}
