package com.SmartContactManager.demoSmartContact.Config;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.SmartContactManager.demoSmartContact.entity.User1;
import com.SmartContactManager.demoSmartContact.entity.provider;
import com.SmartContactManager.demoSmartContact.entity.helper.AppConstant;
import com.SmartContactManager.demoSmartContact.repository.userrepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSucessHandler.class);


    @Autowired
    private userrepo userr;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

            logger.info("User authenticated via OAuth2: " + authentication.getName());

            //identify the provider
            OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
            logger.info(authorizedClientRegistrationId);


           DefaultOAuth2User oauthUser =  (DefaultOAuth2User)authentication.getPrincipal();
           oauthUser.getAttributes().forEach((key,value)->{

           });


           User1 use = new User1();
           use.setUserid(UUID.randomUUID().toString());
           use.setRolelist(List.of(AppConstant.ROLE_USER));
           use.setEmailverification(true);
           use.setEnable(true);
           use.setPassword("dummy");


            if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

                use.setEmail(oauthUser.getAttribute("email").toString());
                use.setProfilepic(oauthUser.getAttribute("picture").toString());
                use.setProvideruserid(oauthUser.getName());
                use.setPROV(provider.GOOGLE);
                use.setAbout("this account is created by google");
                use.setName(oauthUser.getAttribute("name")); 


                
            }else if(authorizedClientRegistrationId.equalsIgnoreCase("github")) {


                String email = oauthUser.getAttribute("email") !=null ?
                oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString() + "@gmail.com";
                String picture = oauthUser.getAttribute("avatar_url").toString();
                String name = oauthUser.getAttribute("login").toString();
                String provideruserid = oauthUser.getName();

                use.setEmail(email);
                use.setProfilepic(picture);
                use.setName(name);
                use.setProvideruserid(provideruserid);
                use.setPROV(provider.GITHUB);
                use.setAbout("this account is created by github");


            }else if(authorizedClientRegistrationId.equalsIgnoreCase("Linkedin")){

            }else{
                logger.info("OAuthAuthenticationSuccessHandler : Unknown provider");
            }
            /* 
            DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();

            // logger.info(user.getName());
            // user.getAttributes().forEach((key, value) ->{
            //     logger.info("{} => {}", key, value);
            // });

            // logger.info(user.getAuthorities().toString());

            // data datbase .

            String email = user.getAttribute("email").toString();
            String name = user.getAttribute("name").toString();
            String picture = user.getAttribute("picture").toString();


            // create user and save in database

            User1 uu = new User1();
            uu.setEmail(email);
            uu.setName(name);
            uu.setProfilepic(picture);
            uu.setPassword("password");
            uu.setUserid(UUID.randomUUID().toString());
            uu.setPROV(provider.GOOGLE);
            uu.setEnable(true);
            uu.setEmailverification(true);
            uu.setProvideruserid(user.getName());
            uu.setRolelist(List.of(AppConstant.ROLE_USER));
            uu.setAbout("this account is created using google ");


            User1 uu1 = userr.findByEmail(email).orElse(null);
            if(uu1 == null)
            {
                userr.save(uu);
                logger.info("user saved"+ email);
            }

            */


            User1 uu1 = userr.findByEmail(use.getEmail()).orElse(null);
            if(uu1 == null)
            {
                userr.save(use);
                logger.info("user saved"+ use.getEmail());
            }


            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
        
        }

            
}