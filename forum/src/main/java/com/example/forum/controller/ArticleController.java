package com.example.forum.controller;


import com.example.forum.common.AppResult;
import com.example.forum.common.ResultCode;
import com.example.forum.config.AppConfig;
import com.example.forum.model.Article;
import com.example.forum.model.Board;
import com.example.forum.model.User;
import com.example.forum.services.IArticleService;
import com.example.forum.services.IBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "文章接口")
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private IArticleService articleService;
    @Resource
    private IBoardService boardService;

    /**
     * 发布新帖子
     * @param boardId 版块Id
     * @param title 文章标题
     * @param content 文章内容
     * @return
     */
    @ApiOperation("发布新帖")
    @PostMapping("/create")
    public AppResult create (HttpServletRequest request,
                             @ApiParam("版块Id") @RequestParam("boardId") @NonNull Long boardId,
                             @ApiParam("文章标题") @RequestParam("title") @NonNull String title,
                             @ApiParam("文章内容") @RequestParam("content") @NonNull String content) {
        // 校验用户是否禁言
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        if (user.getState() == 1) {
            // 用户已禁言
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        // 版块的校验
        Board board = boardService.selectById(boardId.longValue());
        if (board == null || board.getDeleteState() == 1 || board.getState() == 1) {
            // 打印日志
            log.warn(ResultCode.FAILED_BOARD_BANNED.toString());
            // 返回响应
            return AppResult.failed(ResultCode.FAILED_BOARD_BANNED);
        }
        // 封装文章对象
        Article article = new Article();
        article.setTitle(title); // 标题
        article.setContent(content); // 正文
        article.setBoardId(boardId); // 版块Id
        article.setUserId(user.getId()); // 作者Id
        // 调用Service
        articleService.create(article);
        // 响应
        return AppResult.success();
    }

    @ApiOperation("获取帖子列表")
    @GetMapping("/getAllByBoardId")
    public AppResult<List<Article>> getAllByBoardId (@ApiParam("版块Id") @RequestParam(value = "boardId", required = false) Long boardId) {

        // 定义返回的集合
        List<Article> articles;
        // 判断传入的参数是否为空
        if (boardId == null) {
            // 如果传入的参数为空，查询所有
            articles = articleService.selectAll();
        } else {
            // 如果传入的版块Id不为空，查询指定版块下的帖子列表
            articles = articleService.selectAllByBoardId(boardId);
        }

        // 结果是否为空
        if (articles == null) {
            // 如果结合集为空，那么创建上个空集合
            articles = new ArrayList<>();
        }
        // 响应结果
        return AppResult.success(articles);
    }


    @ApiOperation("根据帖子Id获取详情")
    @GetMapping("/details")
    public AppResult<Article> getDetails (HttpServletRequest request,
                                          @ApiParam("帖子Id") @RequestParam("id") @NonNull Long id) {
        // 从session中获取当前登录的用户
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);

        // 调用Service，获取帖子详情
        Article article = articleService.selectDetailById(id);
        // 判断结果是否为空
        if (article == null) {
            // 返回错误信息
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }
        // 判断当前用户是否为作者
        if (user.getId() == article.getUserId()) {
            // 标识为作者
            article.setOwn(true);
        }
        // 返回结果
        return AppResult.success(article);
    }


    @ApiOperation("修改帖子")
    @PostMapping("/modify")
    public AppResult modify (HttpServletRequest request,
                             @ApiParam("帖子Id") @RequestParam("id") @NonNull Long id,
                             @ApiParam("帖子标题") @RequestParam("title") @NonNull String title,
                             @ApiParam("帖子正文") @RequestParam("content") @NonNull String content) {
        // 获取当前登录的用户
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        // 校验用户状态
        if (user.getState() == 1) {
            // 返回错误描述
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        // 查询帖子详情
        Article article = articleService.selectById(id);
        // 校验帖子是否有效
        if (article == null) {
            // 返回错误描述
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }
        // 判断用户是不是作者
        if (user.getId() != article.getUserId()) {
            // 返回错误描述
            return AppResult.failed(ResultCode.FAILED_FORBIDDEN);
        }
        // 判断帖子的状态 - 已归档
        if (article.getState() == 1 || article.getDeleteState() == 1) {
            // 返回错误描述
            return AppResult.failed(ResultCode.FAILED_ARTICLE_BANNED);
        }
        // 调用Service
        articleService.modify(id, title, content);
        // 打印日志
        log.info("帖子更新成功. Article id = " + id + "User id = " + user.getId() + ".");
        // 返回正确的结果
        return AppResult.success();
    }
}
