package com.administrator.service.impl;

import com.administrator.dao.AdministratorDao;
import com.administrator.entity.Administrator;
import com.administrator.service.AdministratorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorDao, Administrator> implements AdministratorService {

    @Autowired
    private AdministratorDao administratorDao;
}
