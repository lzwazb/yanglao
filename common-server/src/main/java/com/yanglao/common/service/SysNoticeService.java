package com.yanglao.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanglao.common.entity.SysNotice;

import java.util.List;

public interface SysNoticeService extends IService<SysNotice> {
    
    /**
     * 获取所有已发布公告(用户端)
     * @return 公告列表
     */
    List<SysNotice> getPublishedList();

    /**
     * 分页获取公告(管理端)
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param title 标题(可选)
     * @return 分页结果
     */
    Page<SysNotice> getNoticePage(int pageNum, int pageSize, String title);

    /**
     * 发布公告
     * @param notice 公告信息
     * @return 是否成功
     */
    boolean addNotice(SysNotice notice);

    /**
     * 修改公告
     * @param notice 公告信息
     * @return 是否成功
     */
    boolean updateNotice(SysNotice notice);
}
