package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    public void addLoggedInUserInformation(Model model, Authentication authentication) 
    {
        
    }
    
    // user dashboard page
    @RequestMapping("/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }
    
    // user profile page
    @RequestMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}", username);

        // getting data from database
        User user = userService.getUserByEmail(username);
        System.out.println(user.getName());

        model.addAttribute("user", user);

        System.out.println("User Profile");
        return "user/profile";
    }
    

    // user add contact page

    // user view contacts 

    // user edit contact 

    // user delete contact 

    // user search contact 
}
