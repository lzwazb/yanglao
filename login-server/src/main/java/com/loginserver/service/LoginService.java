package com.loginserver.service;


import com.loginserver.entity.Administrator;
import com.loginserver.entity.dto.AdministratorDto;

public interface LoginService {

    Administrator adminLogin(AdministratorDto administratorDto);
}

