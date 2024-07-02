package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private final Logger logger = LoggerFactory.getLogger(ContactController.class);
    
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;



    // add contact page handler
    @RequestMapping("/add")
    public String addContactView(Model model) {

        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }



    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result ,Authentication authentication, HttpSession session) {

        // get the loggedin user
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);


        // validate form
        if(result.hasErrors()) {
            session.setAttribute("message", Message.builder()
            .content("Please correct the following errors")
            .type(MessageType.red)
            .build());
            return "user/add_contact";
        }


        // processs contact picture 
        logger.info("file information : {}", contactForm.getContactImage().getOriginalFilename());
        String fileURL = imageService.uploadImage(contactForm.getContactImage());


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
        contact.setPicture(fileURL);

        // save contact
        contactService.save(contact);

        // set message to be displayed on view 
        session.setAttribute("message", Message.builder()
        .content("You have successfully added a new contact.")
        .type(MessageType.green)
        .build());

        return "redirect:/user/contacts/add";
    }
}
