package com.SmartContactManager.demoSmartContact.Controller;

import java.util.*;

import org.hibernate.boot.model.source.internal.hbm.Helper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.SmartContactManager.demoSmartContact.entity.Contact;
import com.SmartContactManager.demoSmartContact.entity.User1;
import com.SmartContactManager.demoSmartContact.entity.helper.AppConstant;
import com.SmartContactManager.demoSmartContact.entity.helper.Message;
import com.SmartContactManager.demoSmartContact.entity.helper.helper;
import com.SmartContactManager.demoSmartContact.entity.helper.messagetype;
import com.SmartContactManager.demoSmartContact.form.ContactForm;
import com.SmartContactManager.demoSmartContact.services.ContactService;
import com.SmartContactManager.demoSmartContact.services.ImageService;
import com.SmartContactManager.demoSmartContact.services.userservice;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/user/contact")
public class ContactController {
    //add contact page handler

    
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private ContactService contactservice;

    @Autowired
    private userservice userser;


   @GetMapping("/add")
   public String addContactView(Model model)
   {
       ContactForm contactform = new ContactForm();
       //contactform.setName("jagmeet singh");
       model.addAttribute("contactform", contactform);
       System.out.println("this is get method");
       return "user/add_contact";
   }

   

   @PostMapping("/add")
   public String saveContact(@Valid @ModelAttribute("contactform") ContactForm contactForm,BindingResult result, Authentication authentication, Model model, HttpSession session)
    {


        
        //validate  the form

        if(result.hasErrors())
        {

            result.getAllErrors().forEach(error->logger.info(error.toString()));

            model.addAttribute("contactform", contactForm);
            session.setAttribute("message", Message.builder()
            .content("please correct the following errors")
            .type(messagetype.red)
            .build()
            );
            return "user/add_contact";
        }

        String username  = helper.getEmailOfLoggedUser(authentication);

        User1 uu= userser.getUserbyemail(username);

        logger.info("********************   *********  * ********** File Information #############  #################  #{}",contactForm.getContactImage().getOriginalFilename());

        // process the contact picture
        String filename = UUID.randomUUID().toString();


        String fileURL = imageService.uploadImage(contactForm.getContactImage(),filename);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFav());
        contact.setEmail(contactForm.getEmail());
        contact.setPhonenumber(contactForm.getPhonenumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDesc());
        contact.setUser1(uu);
        contact.setLinkedinlink(contactForm.getLinkedin());
        contact.setWebsitelink(contactForm.getWebsitelink());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(fileURL);
        //set the contact picture url


        //set meassage to display




        contactservice.save(contact);

        System.out.println("this is post method");
        System.out.println(contactForm);


        session.setAttribute("message", Message.builder()
            .content("you have sucessfully added the contact")
            .type(messagetype.green)
            .build()
            );
        return "redirect:/user/contact/add";
    } 
    
    @RequestMapping
    public String veiwContact( @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value= "size", defaultValue=AppConstant.PAGE_SIZE+"") int size, @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, @RequestParam(value = "direction", defaultValue = "asc") String direction ,Model model, Authentication authentication)
    {

       String username = helper.getEmailOfLoggedUser(authentication);

       User1 uu = userser.getUserbyemail(username);

       Page<Contact> pageContact = contactservice.getByUser(uu,page,size,sortBy,direction);
       
    //    pageContact.getContent()

       model.addAttribute("pageContact", pageContact);
       model.addAttribute("pageSize", AppConstant.PAGE_SIZE);

        return "user/contacts";
    }

}
