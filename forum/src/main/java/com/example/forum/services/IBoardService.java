package com.example.forum.services;

import com.example.forum.model.Board;

import java.util.List;

public interface IBoardService {
    /**
     * 查询num条记录
     * @param num 要查询的条数
     * @return
     */
    List<Board> selectByNum (Integer num);
}
