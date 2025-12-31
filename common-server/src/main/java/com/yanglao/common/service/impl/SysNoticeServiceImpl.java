package com.yanglao.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanglao.common.entity.SysNotice;
import com.yanglao.common.mapper.SysNoticeMapper;
import com.yanglao.common.service.SysNoticeService;
import org.springframework.stereotype.Service;

@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
}
