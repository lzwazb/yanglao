package com.yanglao.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanglao.common.entity.SysNotice;
import com.yanglao.common.mapper.SysNoticeMapper;
import com.yanglao.common.service.SysNoticeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    @Override
    public List<SysNotice> getPublishedList() {
        return this.list(new LambdaQueryWrapper<SysNotice>()
                .eq(SysNotice::getStatus, 1)
                .orderByDesc(SysNotice::getCreateTime));
    }

    @Override
    public Page<SysNotice> getNoticePage(int pageNum, int pageSize, String title) {
        LambdaQueryWrapper<SysNotice> wrapper = new LambdaQueryWrapper<>();
        if (title != null && !title.isEmpty()) {
            wrapper.like(SysNotice::getTitle, title);
        }
        wrapper.orderByDesc(SysNotice::getCreateTime);
        return this.page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public boolean addNotice(SysNotice notice) {
        notice.setCreateTime(LocalDateTime.now());
        notice.setUpdateTime(LocalDateTime.now());
        // 默认发布状态
        if (notice.getStatus() == null) {
            notice.setStatus(1);
        }
        return this.save(notice);
    }

    @Override
    public boolean updateNotice(SysNotice notice) {
        notice.setUpdateTime(LocalDateTime.now());
        return this.updateById(notice);
    }
}
