package com.example.forum.services.impl;

import com.example.forum.model.User;
import com.example.forum.services.IUserService;
import com.example.forum.utils.MD5Util;
import com.example.forum.utils.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private IUserService userService;


    @Test
    @Transactional
    void createNormalUser() {

        // 构造User对象
        User user = new User();
        user.setUsername("bitboy1");
        user.setNickname("bitboy");

        // 定义一个原始的密码
        String password = "123456";
        // 生成盐
        String salt = UUIDUtil.UUID_32();
        // 生成密码的密文
        String ciphertext = MD5Util.md5Salt(password, salt);
        // 设置加密后的密码
        user.setPassword(ciphertext);
        // 设置盐
        user.setSalt(salt);
        // 调用Service层的方法
        userService.createNormalUser(user);

        // 打印结果
        System.out.println(user);

    }

    @Test
    void selectByUserName() {
        User user = userService.selectByUserName("bitboy1");
        System.out.println(user);
    }

    @Test
    void login() {
        User user = userService.login("bitboy1","123456");
        System.out.println(user);
    }

    @Test
    void selectById() {
        User user = userService.selectById(1l);
        System.out.println(user);
    }

    @Test
    @Transactional
    void addOneArticleCountById() {
        userService.addOneArticleCountById(1l);
        System.out.println("更新成功");
    }

    @Test
    @Transactional
    void subOneArticleCountById() {
    }
}