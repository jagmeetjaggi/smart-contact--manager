package com.SmartContactManager.demoSmartContact.entity.helper;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class helper {

    public static String getEmailOfLoggedUser(Authentication authentication)
    {
        if(authentication instanceof OAuth2AuthenticationToken)
        {
            var oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            String clientid = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User = (OAuth2User)authentication.getPrincipal();


            String username = "";

            if(clientid.equalsIgnoreCase("google"))
            {
                System.out.println("getting email from google");
               username = oauth2User.getAttribute("email").toString();
            }else if(clientid.equalsIgnoreCase("github"))
            {
                System.out.println("getting email from github:");

                username = oauth2User.getAttribute("email") !=null ?
                oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString() + "@gmail.com";
                

            }





            return username;

        }else{
            System.out.println("getting data from local:");

            return authentication.getName();
        }
    }

}
