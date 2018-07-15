package com.fsats.mianshi.dao;

import com.fsats.mianshi.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper {

    /**
     * 通过用户名和密码查看是否存在用户
     * @param username 用户名
     * @param password 密码
     * @return 受影响行数
     */
    int getUser(@Param("username") String username, @Param("password") String password);

    /**
     * 通过用户名获得用户信息
     * 始终在service中保证用户名是唯一的
     * @param username 用户名
     * @return 用户对象
     */
    Users getUserByName(@Param("username") String username);

    /**
     * 检查用户是否存在
     * 用在登录前检查，避免无效用户登录。注册前检查
     * @param username
     * @return
     */
    int checkUser(@Param("username") String username);

    /**
     * 添加用户
     * @param username
     * @param password
     * @return
     */
    int addUser(@Param("username") String username,@Param("password") String password);

    int editUser(Users users);

    Users getUserById(@Param("id") Integer id);

    String getpwdById(@Param("id") Integer id);

    int editpwdById(@Param("id") Integer id,@Param("pwd") String pwd);
}
