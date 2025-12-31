package com.yanglao.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanglao.business.entity.ServiceReview;
import com.yanglao.business.mapper.ServiceReviewMapper;
import com.yanglao.business.service.ServiceReviewService;
import org.springframework.stereotype.Service;

@Service
public class ServiceReviewServiceImpl extends ServiceImpl<ServiceReviewMapper, ServiceReview> implements ServiceReviewService {
}
