package com.epam.brest.courses.web;

import com.epam.brest.courses.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by alesya on 10.11.14.
 */

@Controller
@RequestMapping("/mvc")
public class UserController {

    @RequestMapping("/")
    public ModelAndView launchInputForm(){
        ModelAndView view = new ModelAndView("inputForm", "user", new User());
        return view;

    }
}
