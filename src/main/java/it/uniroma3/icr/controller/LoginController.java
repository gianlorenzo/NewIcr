package it.uniroma3.icr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.uniroma3.icr.instagramConfig.InstagramJPService;
import it.uniroma3.icr.instagramConfig.UserInstagram;
import it.uniroma3.icr.service.impl.StudentServiceSocial;
import it.uniroma3.icr.supportControllerMethod.FacebookControllerSupport;
import it.uniroma3.icr.supportControllerMethod.GoogleControllerSupport;
import org.codehaus.jackson.map.ObjectMapper;
import org.jinstagram.Instagram;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.StringWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.service.impl.StudentService;
import it.uniroma3.icr.service.impl.TaskService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.StringWriter;

@Controller
public class LoginController  {

	@Autowired
	public TaskService taskService;

	@Autowired
	public StudentService studentService;

	@Autowired
	private StudentServiceSocial userFacadesocial;

	private ConnectionRepository connectionRepository;

	private Google google;

	private FacebookControllerSupport facebookControllerSupport = new FacebookControllerSupport();

	private GoogleControllerSupport googleControllerSupport = new GoogleControllerSupport();

	public LoginController(Facebook facebook, Google google, ConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
		this.google = google;
		this.connectionRepository = connectionRepository;
	}

	@RequestMapping(value = "/facebookLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String helloFacebook(@RequestParam(value = "daFB", required = false) String daFB, Model model,
								@ModelAttribute("social") String social, RedirectAttributes redirectAttributes) {

		Facebook facebook = connectionRepository.findPrimaryConnection(Facebook.class).getApi();
		if (daFB == null)
			return "redirect:/login";
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null)
			return "redirect:/connect/facebook";

		return facebookControllerSupport.facebookLogin(facebook,userFacadesocial,model,social,redirectAttributes);

	}


	@RequestMapping("/instagram")
	@ResponseBody
	public String invoca(@RequestParam String code) throws Exception {
		InstagramJPService instagramObj = new InstagramJPService();
		instagramObj.build();
		Instagram instagram = instagramObj.getInstagram(code);
		UserInfo userInfo = instagram.getCurrentUserInfo();
		UserInstagram userInstagram = new UserInstagram(userInfo);
		ObjectMapper mapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		mapper.writeValue(stringWriter, userInstagram);

		return stringWriter.toString();
	}


	@RequestMapping(value="/googleLogin", method = {RequestMethod.GET, RequestMethod.POST})
	public String helloGoogle(@RequestParam(value = "daGoogle", required = false)String daGoogle, Model model,
							  @ModelAttribute("social") String social,RedirectAttributes redirectAttributes) {

		if(daGoogle==null)
			return "redirect:/login";
		if (this.connectionRepository.findPrimaryConnection(Google.class) == null)
			return "redirect:/connect/google";

		return googleControllerSupport.googleLogin(google,connectionRepository,userFacadesocial, model, social, redirectAttributes);

	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login (ModelMap model,@RequestParam(value = "error", required = false) String error) {
		if (error != null) {
			model.addAttribute("error", "Username o password non validi");
		}
		model.addAttribute("user", new Student());
		model.addAttribute("admin", new Administrator());
		return "login";
	}

	@RequestMapping(value="*/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}

	@RequestMapping(value="/role", method = RequestMethod.GET)
	public String loginRole(Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toString();
		Student student = studentService.findUser(auth.getName());
		String targetUrl = "";
		if(role.contains("ROLE_USER")) {
			model.addAttribute("student", student);
			targetUrl = "redirect:/user/homeStudent";
		} else if(role.contains("ROLE_ADMIN")) {
			targetUrl = "redirect:/admin/homeAdmin";
		}
		return targetUrl;
	}
}