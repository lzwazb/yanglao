package com.yanglao.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanglao.common.entity.SysNotice;
import com.yanglao.common.service.SysNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notice")
@Tag(name = "公告管理")
public class SysNoticeController {

    @Autowired
    private SysNoticeService sysNoticeService;

    @Operation(summary = "获取所有已发布公告(用户端)")
    @GetMapping("/list")
    public List<SysNotice> list() {
        return sysNoticeService.list(new LambdaQueryWrapper<SysNotice>()
                .eq(SysNotice::getStatus, 1)
                .orderByDesc(SysNotice::getCreateTime));
    }

    @Operation(summary = "分页获取公告(管理端)")
    @GetMapping("/page")
    public Page<SysNotice> page(@RequestParam(defaultValue = "1") int pageNum,
                                @RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(required = false) String title) {
        LambdaQueryWrapper<SysNotice> wrapper = new LambdaQueryWrapper<>();
        if (title != null && !title.isEmpty()) {
            wrapper.like(SysNotice::getTitle, title);
        }
        wrapper.orderByDesc(SysNotice::getCreateTime);
        return sysNoticeService.page(new Page<>(pageNum, pageSize), wrapper);
    }


    @Operation(summary = "发布公告")
    @PostMapping("/add")
    public boolean add(@RequestBody SysNotice notice) {
        notice.setCreateTime(LocalDateTime.now());
        notice.setUpdateTime(LocalDateTime.now());
        // 默认发布状态
        if (notice.getStatus() == null) {
            notice.setStatus(1);
        }
        return sysNoticeService.save(notice);
    }

    @Operation(summary = "修改公告")
    @PostMapping("/update")
    public boolean update(@RequestBody SysNotice notice) {
        notice.setUpdateTime(LocalDateTime.now());
        return sysNoticeService.updateById(notice);
    }

    @Operation(summary = "删除公告")
    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return sysNoticeService.removeById(id);
    }
}
