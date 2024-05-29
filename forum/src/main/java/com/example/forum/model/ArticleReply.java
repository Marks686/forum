package com.example.forum.model;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleReply {
    // 编号
    private Long id;

    // 帖子Id 关联article表
    private Long articleId;

    // 回复的用户编号
    private Long postUserId;

    // 需求中楼中楼
    private Long replyId;

    private Long replyUserId;

    // 回复正文
    private String content;

    // 点赞功能
    private Integer likeCount;

    // 状态 0正常 1禁用
    private Byte state;


    // 状态 0正常 1禁用
    private Byte deleteState;

    // 创建时间
    private Date createTime;


    // 更新时间
    private Date updateTime;

    // 关联对象 - 回复的发布者
    private User user;
}