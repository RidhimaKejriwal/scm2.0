package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {
    
    // user dashboard page
    @RequestMapping("/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }
    
    // user profile page
    @RequestMapping("/profile")
    public String userProfile() {
        return "user/profile";
    }
    

    // user add contact page

    // user view contacts 

    // user edit contact 

    // user delete contact 

    // user search contact 
}
