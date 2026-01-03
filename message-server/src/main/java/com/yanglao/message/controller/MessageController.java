package com.yanglao.message.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanglao.message.entity.Message;
import com.yanglao.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/message")
@Tag(name = "留言管理")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "分页获取留言(管理端)")
    @GetMapping("/page")
    public Page<Message> page(@RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "10") int pageSize,
                              @RequestParam(required = false) Integer status) {
        return messageService.getMessagePage(pageNum, pageSize, status);
    }

    @Operation(summary = "发布留言(用户端)")
    @PostMapping("/add")
    public boolean add(@RequestBody Message message) {
        message.setCreateTime(LocalDateTime.now());
        message.setStatus(0); // 默认为未回复
        return messageService.save(message);
    }

    @Operation(summary = "管理员回复留言")
    @PostMapping("/reply")
    public boolean reply(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        String replyContent = (String) params.get("replyContent");
        String replyUser = (String) params.get("replyUser");
        return messageService.replyMessage(id, replyContent, replyUser);
    }

    @Operation(summary = "用户回复/追问留言")
    @PostMapping("/user/reply")
    public boolean userReply(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        String content = (String) params.get("content");
        String username = (String) params.get("username");
        return messageService.userReplyMessage(id, content, username);
    }

    @Operation(summary = "删除留言")
    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return messageService.removeById(id);
    }
}
