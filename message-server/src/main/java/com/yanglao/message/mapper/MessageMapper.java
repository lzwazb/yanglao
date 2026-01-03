package com.yanglao.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanglao.message.entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
