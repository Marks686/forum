package com.example.forum.services.impl;

import com.example.forum.model.Article;
import com.example.forum.services.IArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
class ArticleServiceImplTest {

    @Resource
    private IArticleService articleService;


    @Test
    @Transactional
    void create() {
        Article article = new Article();
        article.setUserId(1l);
        article.setBoardId(1l);  // Java板块
        article.setTitle("单元测试");
        article.setContent("测试内容");
        articleService.create(article);
        System.out.println("发帖成功");
    }
}