package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    
    @RequestMapping("/home")
    public String home()
    {
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage()
    {
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage()
    {
        return "services";
    }
    
    @RequestMapping("/login")
    public String loginPage()
    {
        return "login";
    }

    @RequestMapping("/signup")
    public String signupPage()
    {
        return "register";
    }
    @RequestMapping("/contact")
    public String contactPage()
    {
        return "contact";
    }

}
