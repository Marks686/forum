package com.example.forum.services.impl;

import com.example.forum.model.Article;
import com.example.forum.services.IArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ArticleServiceImplTest {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private IArticleService articleService;


    @Test
    void create() {
        Article article = new Article();
        article.setUserId(1l);
        article.setBoardId(1l);  // Java板块
        article.setTitle("单元测试");
        article.setContent("测试内容");
        articleService.create(article);
        System.out.println("发帖成功");
    }

    @Test
    void selectAll() throws JsonProcessingException {

        // 调用service
        List<Article> articles = articleService.selectAll();
        // 转换成JSON字符串并打印
        System.out.println(objectMapper.writeValueAsString(articles));
    }

    @Test
    void selectAllByBoardId() throws JsonProcessingException {
        List<Article> articles = articleService.selectAllByBoardId(2l);
        System.out.println(objectMapper.writeValueAsString(articles));
    }

    @Test
    void selectDetailById() throws JsonProcessingException {
        Article article = articleService.selectDetailById(100l);
        System.out.println(objectMapper.writeValueAsString(article));
    }
}