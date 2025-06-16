package com.SmartContactManager.demoSmartContact.Controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.SmartContactManager.demoSmartContact.entity.User1;
import com.SmartContactManager.demoSmartContact.entity.helper.ResourceNotFoundException;
import com.SmartContactManager.demoSmartContact.entity.helper.helper;
import com.SmartContactManager.demoSmartContact.services.userservice;


@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private userservice uu;
    //user dashborad

    @RequestMapping(value = "/dashboard", method=RequestMethod.GET)
    public String userdashboard() {

        System.out.println("user dashboard : ");
    return "user/dashboard";
    }

    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String userProfile(Model model,Authentication authentication) {


       String username = helper.getEmailOfLoggedUser(authentication);
        logger.info("user logged in : {}", username);

        // fetch from the database
        try{
            User1 u =  uu.getUserbyemail(username);
            logger.info("User found: {}", u.getName());
            model.addAttribute("loggedin",u);
            return "user/profile";


        }catch(ResourceNotFoundException ee)
        {
            logger.error("User not found for email: {}", username);

        }

        return "user/profile";
    }
    
    

}
