package com.example.forum.services.impl;

import com.example.forum.model.ArticleReply;
import com.example.forum.services.IArticleReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ArticleReplyServiceImplTest {

    @Resource
    private IArticleReplyService articleReplyService;

    @Test
    void create() {
        // 构造一个回复对象
        ArticleReply articleReply = new ArticleReply();
        articleReply.setArticleId(8l);
        articleReply.setPostUserId(1l);
        articleReply.setContent("单元测试回复");
        articleReplyService.create(articleReply);
        System.out.println("回复成功");
    }
}