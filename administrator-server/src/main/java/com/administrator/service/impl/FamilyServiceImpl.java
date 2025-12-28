package com.administrator.service.impl;

import com.administrator.dao.FamilyDao;
import com.administrator.entity.Family;
import com.administrator.service.FamilyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyServiceImpl extends ServiceImpl<FamilyDao, Family> implements FamilyService {

    @Autowired
    private FamilyDao familyDao;

}
