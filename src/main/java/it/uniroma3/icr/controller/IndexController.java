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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goToIndex() {

        return "index";
    }

    @RequestMapping("/")
    public void handleRequest() {
        throw new RuntimeException("test exception");
    }


}
