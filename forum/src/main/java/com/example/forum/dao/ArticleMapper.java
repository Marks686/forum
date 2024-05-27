package com.example.forum.dao;

import com.example.forum.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    int insert(Article row);

    int insertSelective(Article row);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article row);

    int updateByPrimaryKeyWithBLOBs(Article row);

    int updateByPrimaryKey(Article row);

    /**
     * 查询所有帖子列表
     * @return
     */
    List<Article> selectAll();

    /**
     * 根据板块Id查询所有的帖子列表
     * @param boardId
     * @return
     */
    List<Article> selectAllByBoardId(@Param("boardId") Long boardId);


    /**
     * 根据帖子Id查询详情
     * @param id
     * @return
     */
    Article selectDetailById(@Param("id") Long id);
}