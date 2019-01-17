package it.uniroma3.icr.cloud.controller;


import it.uniroma3.icr.cloud.restInterface.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CloudController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    final RestClient restClient;

    @Autowired
    public CloudController(RestClient restClient) {
        this.restClient = restClient;
    }
/*
    @RequestMapping(value = "/provaFeign")
    public String getProva() {

        return restClient.getProva().getStringaProva();
    }

*/
}
