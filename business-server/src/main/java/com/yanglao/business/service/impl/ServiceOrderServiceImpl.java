package com.yanglao.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanglao.business.entity.ServiceOrder;
import com.yanglao.business.mapper.ServiceOrderMapper;
import com.yanglao.business.service.ServiceOrderService;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrderServiceImpl extends ServiceImpl<ServiceOrderMapper, ServiceOrder> implements ServiceOrderService {
}
