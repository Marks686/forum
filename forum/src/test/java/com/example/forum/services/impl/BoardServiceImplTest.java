package com.example.forum.services.impl;

import com.example.forum.model.Board;
import com.example.forum.services.IBoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class BoardServiceImplTest {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private IBoardService boardService;
    @Test
    void selectByNum() {
        List<Board> boards = boardService.selectByNum(1);
        System.out.println(boards);
    }

    @Test
    @Transactional
    void addOneArticleCountById() {
        boardService.addOneArticleCountById(1l);
        System.out.println("更新成功");
    }

    @Test
    void selectById() throws JsonProcessingException {
        Board board = boardService.selectById(2l);
        System.out.println(objectMapper.writeValueAsString(board));
    }
}