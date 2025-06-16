package com.SmartContactManager.demoSmartContact.entity.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
    public static void removemessage()
    {
        try{
            System.out.println("removing the mesaage");
            HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("message"); 
        }
        catch(Exception ee)
        {
            System.out.println("Error in session helper: "+ee);
            ee.printStackTrace();
        }
       
    }

}
