package com.example.forum.services.impl;

import com.example.forum.model.Message;
import com.example.forum.services.IMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
class MessageServiceImplTest {

    @Resource
    private IMessageService messageService;
    @Resource
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void create() {
        Message message = new Message();
        message.setPostUserId(7l);
        message.setReceiveUserId(8l);
        message.setContent("单元测试");
        messageService.create(message);
        System.out.println("发送成功");

    }
}