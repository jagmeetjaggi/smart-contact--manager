package com.SmartContactManager.demoSmartContact.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.SmartContactManager.demoSmartContact.entity.User1;
import com.SmartContactManager.demoSmartContact.entity.helper.Message;
import com.SmartContactManager.demoSmartContact.entity.helper.messagetype;
import com.SmartContactManager.demoSmartContact.form.UserForm;
import com.SmartContactManager.demoSmartContact.services.userservice;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



@Controller
public class pageController {
	
	@Autowired
	private userservice userser;

	@Autowired
    private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}


    @RequestMapping("/home")
	public String home(Model model)
	{
		model.addAttribute("name", "jagmeet singh");
		model.addAttribute("YoutubleChannel", "sidhumoosewala");
		model.addAttribute("github", "https://github.com/jagmeetjaggi");
		System.out.println("this home controller .. ");
		return "home";
	}


	@RequestMapping("/about")
	public String about() {
		return "about";
	}


	@RequestMapping("/service")
	public String service() {
		return "service";
	}
	

	@GetMapping("/login")
	public String login() {
		return new String("login");
	}
	

	@GetMapping("/register")
	public String register(Model model) {

		UserForm form = new UserForm();
		model.addAttribute("user", form);
		return new String("register");
	}

	@GetMapping("/contact")
	public String contact() {
		return new String("contact");
	}


	@RequestMapping(value="/do-register", method=RequestMethod.POST)
	public String processregister(@Valid @ModelAttribute("user") UserForm user, BindingResult rBindingResult, HttpSession session)
	{
		System.out.println(user);
		System.out.println("register");

		if(rBindingResult.hasErrors())
		{
			return "register";
		}

		// User1 uu = User1.builder()
		// .name(user.getName())
		// .email(user.getEmail())
		// .password(user.getPassword())
		// .about(user.getAbout())
		// .phoneNumber(user.getPhoneno())
		// .profilepic("")
		// .build();


		User1 us = new User1();
		us.setName(user.getName());
		us.setEmail(user.getEmail());
		us.setPassword(user.getPassword());
		us.setPhoneNumber(user.getPhoneno());
		us.setAbout(user.getAbout());
		us.setProfilepic("");
		

		User1 saveduser = userser.saveUser(us);
		System.out.println("user saved :");


		Message msg =Message.builder().content("Registration Successfull").type(messagetype.green).build();
		session.setAttribute("message",msg);
		return "redirect:/register";
	}	


 @GetMapping("/do-logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/login?logout";
    }


	
}
