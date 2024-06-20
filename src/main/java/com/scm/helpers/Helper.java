package com.scm.helpers;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    
    public static String getEmailOfLoggedInUser(Authentication authentication) {
        
        if (authentication instanceof  OAuth2AuthenticationToken aOAuth2AuthenticationToken) {

            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User = (OAuth2User) authentication.getPrincipal();

            String username = "";

            if(clientId.equalsIgnoreCase("google"))
            {
                // google sign in
                System.out.println("getting email from google");
                username = oauth2User.getAttribute("email");

            }
            else if(clientId.equalsIgnoreCase("github")) 
            {
                // github sign in
                System.out.println("getting email from github");
                username = oauth2User.getAttribute("email")!=null ? oauth2User.getAttribute("email") : oauth2User.getAttribute("login")+"@gmail.com";

            }

            return username;
        }
        else {
            // email id and password login
            System.out.println("getting data from local database"); 
            return authentication.getName();
        }
    }

}
