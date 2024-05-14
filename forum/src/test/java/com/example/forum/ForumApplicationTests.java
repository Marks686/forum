package com.example.forum;

import com.example.forum.dao.UserMapper;
import com.example.forum.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@SpringBootTest
class ForumApplicationTests {

    // 数据源
    @Resource
    private DataSource dataSource;

//     用户的Mapper
    @Resource
    private UserMapper userMapper;

    @Test
    void testConnection() throws SQLException {
        System.out.println("dataSource = " + dataSource.getClass());
        // 获取数据库连接
        Connection connection = dataSource.getConnection();
        System.out.println("connection = " + connection);
        connection.close();
    }

    @Test
    void testMybatis () {
        User user = userMapper.selectByPrimaryKey(1l);
//        System.out.println(user.getUsername());
//        System.out.println(user.getArticleCount());
//        System.out.println(user.getCreateTime());
//        System.out.println(user.getAvatarUrl());
//        System.out.println(user.getDeleteState());
//        System.out.println(user.getEmail());
//        System.out.println(user.getId());
//        System.out.println(user.getIsAdmin());
        System.out.println(user);

    }


    @Test
    void contextLoads() {
        System.out.println("TEST: 基于Spring的论坛系统-前后端分离 ");
    }


}
