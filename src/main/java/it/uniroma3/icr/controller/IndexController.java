package it.uniroma3.icr.controller;

import com.netflix.discovery.converters.Auto;
import it.uniroma3.icr.cloud.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CloudController cloudController;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goToIndex(Model model) {

        String prova = this.cloudController.getProva();
        model.addAttribute("prova",prova);
        return "index";
    }


}
