package com.yanglao.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanglao.message.entity.Message;
import com.yanglao.message.mapper.MessageMapper;
import com.yanglao.message.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public Page<Message> getMessagePage(int pageNum, int pageSize, Integer status) {
        Page<Message> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Message::getStatus, status);
        }
        wrapper.orderByDesc(Message::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public boolean replyMessage(Long id, String replyContent, String replyUser) {
        Message message = this.getById(id);
        if (message == null) {
            return false;
        }
        message.setReplyContent(replyContent);
        message.setReplyUser(replyUser);
        message.setReplyTime(LocalDateTime.now());
        message.setStatus(1); // 设置为已回复
        return this.updateById(message);
    }

    @Override
    public boolean userReplyMessage(Long id, String content, String username) {
        // 用户回复通常是针对已有的留言进行追问或者补充，这里简单实现为追加内容或者新建一条关联留言
        // 根据需求，如果是简单的留言板，用户回复可能意味着修改自己的留言或者追加
        // 但题目要求“用户和管理员均能回复留言”，通常意味着用户可以回复管理员的回复，或者在自己的留言下继续发言
        // 为了简化模型，这里假设用户回复是追加内容到原留言的 content 字段，或者更新 content
        // 但更好的做法可能是新建一条留言，或者 message 表设计支持盖楼。
        // 鉴于表结构已定，且只有 content 和 reply_content，
        // 我们假设用户的“回复”其实是追加问题，或者修改问题。
        // 但如果理解为“对话”，那么表结构可能不支持多轮对话。
        // 另一种理解：用户回复是指用户对管理员的回复进行反馈。
        // 让我们采用追加的方式，或者更新 content 字段，并把 status 改回 0 (未回复)，提示管理员再次回复。
        
        Message message = this.getById(id);
        if (message == null) {
            return false;
        }
        // 追加内容，并标记为未回复，等待管理员再次处理
        String newContent = message.getContent() + "\n\n[用户追问 " + LocalDateTime.now() + "]: " + content;
        message.setContent(newContent);
        message.setStatus(0);
        return this.updateById(message);
    }
}
