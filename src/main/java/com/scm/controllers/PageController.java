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
}
