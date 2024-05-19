package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    
    @RequestMapping("/home")
    public String home()
    {
        System.out.println("home page handler");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage()
    {
        System.out.println("about page handler");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage()
    {
        System.out.println("service page handler");
        return "services";
    }
}
