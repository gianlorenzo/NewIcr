package it.uniroma3.icr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


	@RequestMapping(value="/",  method = RequestMethod.GET)
	public String goToIndex() {
		return "index";
	}



}
