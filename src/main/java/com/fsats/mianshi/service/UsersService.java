package com.fsats.mianshi.service;


import com.fsats.mianshi.entity.Users;

public interface UsersService {
    /**
     * 根据用户名和密码进行用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    boolean login(String username,String password);

    /**
     * 通过用户名获得用户，始终在service中保证username是唯一的
     * @param username 用户名
     * @return 用户对象
     */
    Users getUser(String username);

    /**
     * 用户是否存在
     * @param username 用户名
     * @return 存在证明
     */
    boolean isExist(String username);

    /**
     * 添加用户
     * @param name
     * @param password
     * @return
     */
    boolean addUser(String name,String password);

    /**
     * 更改用户信息
     * @param user
     * @return
     */
    boolean editUser(Users user);

    /**
     * 通过id获得用户
     * @param id
     * @return
     */
    Users getUser(Integer id);

    /**
     * 通过id获得用户密码
     * @param id 用户id
     * @return
     */
    String getPwd(Integer id);

    /**
     * 通过id更改用户密码
     * @param id 用户id
     * @param pwd 要更改的密码
     * @return
     */
    boolean editPwd(Integer id,String pwd);
}
