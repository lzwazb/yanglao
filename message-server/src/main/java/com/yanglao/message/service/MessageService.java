package com.yanglao.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanglao.message.entity.Message;

public interface MessageService extends IService<Message> {
    Page<Message> getMessagePage(int pageNum, int pageSize, Integer status);
    boolean replyMessage(Long id, String replyContent, String replyUser);
    boolean userReplyMessage(Long id, String content, String username);
}
