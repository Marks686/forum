package com.example.forum.services.impl;

import com.example.forum.common.AppResult;
import com.example.forum.common.ResultCode;
import com.example.forum.dao.ArticleMapper;
import com.example.forum.exception.ApplicationException;
import com.example.forum.model.Article;
import com.example.forum.model.Board;
import com.example.forum.model.User;
import com.example.forum.services.IArticleService;
import com.example.forum.services.IBoardService;
import com.example.forum.services.IUserService;
import com.example.forum.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class ArticleServiceImpl implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;

    // 用户和板块操作
    @Resource
    private IUserService userService;
    @Resource
    private IBoardService boardService;

    @Override
    public void create(Article article) {
        // 非空校验
        if (article == null || article.getUserId() == null
            || article.getBoardId() == null
            || StringUtil.isEmpty(article.getTitle())
            || StringUtil.isEmpty(article.getContent())){
            // 打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            // 抛出异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        // 设置默认值
        article.setVisitCount(0); // 访问数
        article.setReplyCount(0); // 回复数
        article.setLikeCount(0);  // 点赞数
        article.setDeleteState((byte) 0);
        article.setState((byte) 0);
        Date data = new Date();
        article.setCreateTime(data);
        article.setUpdateTime(data);

        // 写入数据库
        int articleRow = articleMapper.insertSelective(article);
        if (articleRow <= 0){
            log.warn(ResultCode.FAILED_CREATE.toString());
            throw new ApplicationException((AppResult.failed(ResultCode.FAILED_CREATE)));
        }

        // 获取用户信息
        User user = userService.selectById(article.getUserId());
        if (user == null){
            log.warn(ResultCode.FAILED_CREATE.toString()  + ", 发帖失败, user id = " + article.getUserId());
            throw new ApplicationException((AppResult.failed(ResultCode.FAILED_CREATE)));
        }
        // 更新用户的发帖数
        userService.addOneArticleCountById(user.getId());
        // 获取板块信息
        Board board = boardService.selectById(article.getBoardId());
        // 是否在数据库中有对应的板块
        if (board == null){
            log.warn(ResultCode.FAILED_CREATE.toString()  + ", 发帖失败, board id = " + article.getBoardId());
            throw new ApplicationException((AppResult.failed(ResultCode.FAILED_CREATE)));
        }
        // 更新板块中帖子数量
        boardService.addOneArticleCountById(board.getId());

        // 打印日志
        log.info(ResultCode.SUCCESS.toString() + ", user id = " + article.getUserId()
                + ", board id = " + article.getBoardId() + ", article id  = " + article.getId()
                + "发帖成功");


//        throw new ApplicationException(AppResult.failed("测试事务回滚"));
    }
}
