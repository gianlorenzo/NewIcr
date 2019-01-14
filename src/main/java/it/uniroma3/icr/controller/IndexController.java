package it.uniroma3.icr.controller;

import it.uniroma3.icr.cloud.controller.CloudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CloudController cloudController;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goToIndex(Model model) {
        String id = this.cloudController.getId();

        String prova = this.cloudController.getProva();
        model.addAttribute("prova",prova);
        model.addAttribute("id",id);
        return "index";
    }


}
