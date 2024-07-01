package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.services.ContactService;
import com.scm.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    // add contact page handler
    @RequestMapping("/add")
    public String addContactView(Model model) {

        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result ,Authentication authentication) {

        // get the loggedin user
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        if(result.hasErrors()) {
            return "user/add_contact";
        }

        // TODO: VALIDATE FORM

        // processs contact picture 

        Contact contact = new Contact();

        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        contact.setUser(user);

        // set contact picture url 


        // set message to be displayed on view 

        contactService.save(contact);

        return "redirect:/user/contacts/add";
    }
}
