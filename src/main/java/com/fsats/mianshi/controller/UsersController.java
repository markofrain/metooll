package com.fsats.mianshi.controller;

import com.alibaba.fastjson.JSONObject;
import com.fsats.mianshi.annotation.LoggsType;
import com.fsats.mianshi.annotation.Token;
import com.fsats.mianshi.entity.LoggsTypeE;
import com.fsats.mianshi.entity.Users;
import com.fsats.mianshi.service.UsersService;
import com.google.gson.Gson;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 用户控制器
 * 主要管理用户登录，用户信息，以及用户相关操作
 */
@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    /**
     * 进入登录页面，需要准备登录页面的信息
     * @param model 数据对象
     * @return login页面
     * @throws IOException
     */
    @LoggsType(type = LoggsTypeE.OTHER)
    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("users",new Users());

        //进入login页面，加载login页面所需数据

        return "login";
    }



    /**
     * 用户登录方法
     * 登录成功，进入index页，显示欢迎信息
     * 登录失败，返回登录页面，显示错误信息
     * @param  users 用户对象
     * @param model model对象
     * @return 失败返回请求到login，成功重定向到index
     */
    @LoggsType(type = LoggsTypeE.SELECT)
    @RequestMapping(value = "/login/deal",method = RequestMethod.POST)
    public String login(@Valid Users users, BindingResult bindingResult,
                        Errors errors, Model model, HttpSession session, HttpServletRequest request){
        if (errors.hasErrors()){
            return "login";
        }
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRemoteHost());
        String ret="login", loginInfo="username not exists";
        if(usersService.isExist(users.getUsername())){
            boolean flag = usersService.login(users.getUsername(),users.getPassword());
            if(flag){
                session.setAttribute("user",usersService.getUser(users.getUsername()));
                return "redirect:/index";
            }else{
                ret = "login";
                loginInfo = "username/password fail";
                model.addAttribute("username",users.getUsername());
            }
        }
        model.addAttribute("loginInfo",loginInfo);
        return ret;
    }

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return ajax返回的数据
     */
    @LoggsType(type = LoggsTypeE.OTHER)
    @ResponseBody
    @RequestMapping("register/checkname")
    public Object checkUsername(String username){
        boolean bool = usersService.isExist(username);
        Gson gson = new Gson();
        return gson.toJson(bool);
    }

    /**
     * 检查验证码是否正确
     * @param validateCode 传递来的验证码
     * @param session session会话对象
     * @return
     */
    @LoggsType(type = LoggsTypeE.OTHER)
    @ResponseBody
    @RequestMapping("register/checkcode")
    public Object checkcode(String validateCode,HttpSession session){
        String code = (String) session.getAttribute("key");
        Gson gson = new Gson();
        if (code.equals(validateCode)){
            return gson.toJson("true");
        }else{
            return gson.toJson("false");
        }
    }

    /**
     * 注册方法，进入注册页面
     * @param model 数据对象
     * @return 注册页面
     */
    @LoggsType(type = LoggsTypeE.OTHER)
    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new Users());
        //进入到注册页面所需携带的数据
        return "register";
    }

    /**
     * 注册方法
     * @param user 传来的要新增的用户对象
     * @param result 绑定结果对象
     * @param errors 数据校验的错误对象
     * @param session session会话对象
     * @param model 数据对象
     * @return
     */
    @LoggsType(type = LoggsTypeE.INSERT)
    @RequestMapping("/register/deal")
    public String register(@Valid Users user,BindingResult result,Errors errors,HttpSession session,Model model){
        if(errors.hasErrors()){
            return "register";
        }
        boolean flag = usersService.addUser(user.getUsername(),user.getPassword());
        if(flag){
            session.setAttribute("user",usersService.getUser(user.getUsername()));
            return "redirect:/index";
        }else{
            model.addAttribute("registerInfo","注册失败");
            return "register";
        }

    }

    /**
     * 用户登出
     * @param session session会话对象
     * @return
     */
    @LoggsType(type = LoggsTypeE.OTHER)
    @RequestMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/index";
    }

    /**
     * 显示用户信息，通过session中的user显示
     * @param model
     * @param session
     * @return
     */
    @LoggsType(type = LoggsTypeE.OTHER)
    @RequestMapping("/center/userinfo")
    public String showUserInfo(Model model,HttpSession session){

        return "user/myinfo";
    }

    /**
     * 进入编辑页面
     * @return
     */
    @LoggsType(type = LoggsTypeE.OTHER)
    @RequestMapping("/center/edit-user")
    public String editUser(Model model,HttpSession session){
        model.addAttribute("user", session.getAttribute("user"));
        return "user/edituser";
    }

    /**
     * 进行用户更新
     * @param user
     * @return
     */
    @LoggsType(type = LoggsTypeE.UPDATE)
    @RequestMapping("/center/edit-user/deal")
    public String editUser(Users user,HttpSession session,Model model){
        String message = "";
        Integer userId = ((Users)session.getAttribute("user")).getId();
        user.setId(userId);
        boolean flag = usersService.editUser(user);

        if(flag){
            message = "保存成功";
            Users nowuser = usersService.getUser(userId);
            model.addAttribute("user", nowuser);
            session.setAttribute("user", nowuser);
            System.out.println("-----------"+nowuser);
        }else{
            message = "保存失败";
        }
        model.addAttribute("message", message);

        return "user/edituser";
    }

    @LoggsType(type = LoggsTypeE.OTHER)
    @RequestMapping("/center/edit-pwd")
    public String editpwd(){
        return "user/editpwd";
    }

    /**
     * 更改用户密码
     * 从session中获得id并查询出密码与原密码比较
     * @param ypwd 原密码
     * @param npwd 新密码
     * @return
     */
    @LoggsType(type = LoggsTypeE.UPDATE)
    @RequestMapping("/center/edit-pwd/deal")
    public String editpwd(@Param("ypwd") String ypwd, @Param("npwd") String npwd,HttpSession session,Model model){
        String message = "";
        Users user = (Users) session.getAttribute("user");
        if(!usersService.getPwd(user.getId()).equals(ypwd)){
            message = "原密码错误!";
        }else{
            boolean flag = usersService.editPwd(user.getId(), npwd);
            message="修改成功!";
        }
        model.addAttribute("message", message);
        return "user/editpwd";
    }

}
