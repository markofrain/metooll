package com.fsats.mianshi.controller;

import com.fsats.mianshi.annotation.LoggsType;
import com.fsats.mianshi.entity.LoggsTypeE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @LoggsType(type = LoggsTypeE.OTHER)
    @RequestMapping("/index")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        return modelAndView;
    }


}
