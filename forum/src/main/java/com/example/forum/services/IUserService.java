package com.example.forum.services;

import com.example.forum.model.User;

/**
 * 用户接口
 */
public interface IUserService {

    /**
     * 创建一个普通用户
     * @param user 用户信息
     */
    void createNormalUser(User user);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User selectByUserName(String username);


    /**
     * 处理用户登录
     * @param username
     * @param password
     * @return 用户信息
     */
    User login(String username,String password);
}
