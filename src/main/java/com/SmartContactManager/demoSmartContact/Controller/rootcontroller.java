package com.SmartContactManager.demoSmartContact.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.SmartContactManager.demoSmartContact.entity.User1;
import com.SmartContactManager.demoSmartContact.entity.helper.helper;
import com.SmartContactManager.demoSmartContact.services.userservice;

@ControllerAdvice
public class rootcontroller {
        private Logger logger = LoggerFactory.getLogger(UserController.class);

        @Autowired
        private userservice uu;

        @ModelAttribute
    public void addLoggerUserInformation(Model model, Authentication authentication)
    {

        if(authentication == null)
        {
            return;
        }



        System.out.println("adding logged in user information to the model");
       String username = helper.getEmailOfLoggedUser(authentication);
       logger.info("user logged in : {}", username);

       try{
           User1 u =  uu.getUserbyemail(username);
           
            System.out.println(u.getEmail());
            System.out.println(u.getName());
            model.addAttribute("loggedin", u);
           
       }catch(Exception ee)
       {
           logger.error("User not found for email: {}", username);
       }
    }

}
