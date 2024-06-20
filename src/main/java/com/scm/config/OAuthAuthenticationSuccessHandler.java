package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        // user.getAttributes().forEach((key,value)->{
        //     logger.info("{} => {}", key, value);
        // });
        User user = new User();
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("password");
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        // google
        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            user.setEmail(oauthUser.getAttribute("email"));
            user.setName(oauthUser.getAttribute("name"));
            user.setProfilePic(oauthUser.getAttribute("picture"));
            user.setProvider(Providers.GOOGLE);
            user.setProviderUserId(oauthUser.getName());
            user.setAbout("This account is created using Google.");
        } 
        
        // github
        else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            user.setEmail(oauthUser.getAttribute("email")!=null ? oauthUser.getAttribute("email") : oauthUser.getAttribute("login")+"@gmail.com");
            // user.setEmail(oauthUser.getAttribute("email"));
            user.setName(oauthUser.getAttribute("login"));
            user.setProfilePic(oauthUser.getAttribute("avatar_url"));
            user.setProvider(Providers.GITHUB);
            user.setProviderUserId(oauthUser.getName());
            user.setAbout("This account is created using Github.");
        }

        // create user and save in database
        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
            userRepo.save(user);
            logger.info("User saved: " + user.getEmail());
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }

}
