package com.administrator.controller;


import com.administrator.dao.AdministratorDao;
import com.administrator.entity.Administrator;
import com.administrator.entity.dto.AdministratorDto;
import com.administrator.service.AdministratorService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administrator")
@Tag(name = "管理员接口")
public class AdministratorController {

    @Autowired
    AdministratorService administratorService;












    @PostMapping("/admin")
    public Administrator getAdmin(@RequestBody AdministratorDto administratorDto) {

        Administrator loginAdmin = administratorService.login(administratorDto);

        return loginAdmin;
    }
}
