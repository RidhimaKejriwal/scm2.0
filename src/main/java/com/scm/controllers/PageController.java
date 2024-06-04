package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    
    
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
    public String signupPage(Model model)
    {
        UserForm UserForm = new UserForm();
        model.addAttribute("userForm", UserForm);
        return "register";
    }

    @RequestMapping("/contact")
    public String contactPage()
    {
        return "contact";
    }

    // processing register
    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {
        
        // fetch form data
        System.out.println(userForm);

        // validate form data
        if(rBindingResult.hasErrors()) {
            return "register";
        }

        // save to database
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("default.png");

        User savedUser = userService.saveUser(user);
        System.out.println(savedUser);

        // message = "Registration successful"
        Message message = Message.builder().content("Registration Successful!!").type(MessageType.green).build();
        session.setAttribute("message", message);

        // redirect to page
        return "redirect:/signup";
    }

}
