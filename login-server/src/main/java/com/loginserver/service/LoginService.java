package com.loginserver.service;


import com.loginserver.entity.Administrator;
import com.loginserver.entity.Family;
import com.loginserver.entity.User;
import com.loginserver.entity.dto.AdministratorDto;
import com.loginserver.entity.dto.FamilyDto;
import com.loginserver.entity.dto.UserDto;

public interface LoginService {

    Administrator adminLogin(AdministratorDto administratorDto);

    Family familyLogin(FamilyDto familyDto);

    User userLogin(UserDto userDto);
}
