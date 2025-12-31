package com.yanglao.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanglao.business.entity.ServiceItem;
import com.yanglao.business.mapper.ServiceItemMapper;
import com.yanglao.business.service.ServiceItemService;
import org.springframework.stereotype.Service;

@Service
public class ServiceItemServiceImpl extends ServiceImpl<ServiceItemMapper, ServiceItem> implements ServiceItemService {
}
