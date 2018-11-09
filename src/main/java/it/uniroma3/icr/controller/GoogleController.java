package it.uniroma3.icr.controller;



import it.uniroma3.icr.supportControllerMethod.GoogleControllerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.icr.service.impl.StudentFacadeSocial;

@Controller

public class GoogleController {


	private Google google;
	private ConnectionRepository connectionRepository;

	private GoogleControllerSupport googleControllerSupport = new GoogleControllerSupport();

	public GoogleController(Google google, ConnectionRepository connectionRepository) {
		this.google = google;
		this.connectionRepository = connectionRepository;
	}

	@Autowired
	private StudentFacadeSocial userFacadesocial;

	@RequestMapping(value="/googleLogin", method = {RequestMethod.GET, RequestMethod.POST})
	public String helloGoogle(@RequestParam(value = "daGoogle", required = false)String daGoogle, Model model,
			@ModelAttribute("social") String social,RedirectAttributes redirectAttributes) {

		if(daGoogle==null)
			return "redirect:/login";
		if (this.connectionRepository.findPrimaryConnection(Google.class) == null)
			return "redirect:/connect/google";

		return googleControllerSupport.googleLogin(google,connectionRepository,userFacadesocial, model, social, redirectAttributes);

	}

}
