package com.fsats.mianshi.service.impl;

import com.fsats.mianshi.annotation.LoggsType;
import com.fsats.mianshi.dao.UsersMapper;
import com.fsats.mianshi.entity.LoggsTypeE;
import com.fsats.mianshi.entity.Users;
import com.fsats.mianshi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("usersService")
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public boolean login(String username, String password) {
        //可根据情况进行，用户名密码的验证判断
        int count = usersMapper.getUser(username, password);
        return count > 0 ? true : false;
    }
    @Override
    public Users getUser(String username) {
        if(username==null || username.equals("")){
            return null;
        }else{
            return usersMapper.getUserByName(username);
        }
    }
    @Override
    public boolean isExist(String username) {
        if(username==null || username.equals("")){
            return false;
        }else{
            return usersMapper.checkUser(username)>0?true:false;
        }

    }

    @Override
    public boolean addUser(String name, String password) {
        if(name.equals("")||password.equals("")){
            return false;
        }
        int count = usersMapper.addUser(name,password);
        return count>0?true:false;
    }


    @Override
    public boolean editUser(Users user) {
        int count = usersMapper.editUser(user);

        return count>0?true:false;
    }

    @Override
    public Users getUser(Integer id) {
        return usersMapper.getUserById(id);
    }

    @Override
    public String getPwd(Integer id) {
        String pwd = usersMapper.getpwdById(id);
        return pwd!=null?pwd:"";
    }

    @Override
    public boolean editPwd(Integer id, String pwd) {
        int count = usersMapper.editpwdById(id, pwd);
        return count>0?true:false;
    }
}
