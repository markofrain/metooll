package com.fsats.mianshi.controller;

import com.alibaba.fastjson.JSONObject;
import com.fsats.mianshi.annotation.Token;
import com.fsats.mianshi.entity.Users;
import com.fsats.mianshi.service.UsersService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @RequestMapping("/login")
    public String login(Model model, HttpServletResponse response) throws IOException {
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
    @ResponseBody
    @RequestMapping("register/checkname")
    public Object checkUsername(String username){
        boolean bool = usersService.isExist(username);
        Gson gson = new Gson();
        return gson.toJson(bool);
    }

    /**
     * 检查验证码是否正确
     * @param validateCode
     * @param session
     * @return
     */
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

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new Users());
        //进入到注册页面所需携带的数据
        return "register";
    }

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

    @RequestMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/index";
    }

}
