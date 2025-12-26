package com.administrator.service;

import com.administrator.entity.Administrator;
import com.administrator.entity.dto.AdministratorDto;

public interface AdministratorService {



    //admin登录验证
    Administrator login(AdministratorDto administratorDto);
}
