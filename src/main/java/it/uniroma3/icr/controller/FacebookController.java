package it.uniroma3.icr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniroma3.icr.SupportControllerMethod.FacebookControllerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.icr.model.StudentSocial;
import it.uniroma3.icr.service.impl.StudentFacadeSocial;

@Controller

public class FacebookController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private ConnectionRepository connectionRepository;
	@Autowired
	private StudentFacadeSocial userFacadesocial;

	private FacebookControllerSupport facebookControllerSupport = new FacebookControllerSupport();

	public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
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
}